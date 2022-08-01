package com.basic.cloud.log.aop;

import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.common.utils.IPUtils;
import com.basic.cloud.common.utils.UserUtil;
import com.basic.cloud.log.annotation.EnableLog;
import com.basic.cloud.log.api.SysLogFeignClient;
import com.basic.cloud.log.contstant.LogOperateTypeEnum;
import com.basic.cloud.log.contstant.LogTypeEnum;
import com.basic.cloud.log.entity.SysLog;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Aspect
@Component
public class ReqLogAspect {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(ReqLogAspect.class);

    private final SysLogFeignClient logFeignClient;

    private ExecutorService executorService;

    public ReqLogAspect(SysLogFeignClient logFeignClient) {
        this.logFeignClient = logFeignClient;
    }

    @PostConstruct
    public void init() {
        executorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("business-log-schedule-pool-%d").daemon(true).build());
    }

    @Pointcut("@annotation(com.basic.cloud.log.annotation.EnableLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - startTime;
        UserDetail userDetail = UserUtil.getUser();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        executorService.execute(() -> recordLog(point, time, userDetail, attributes));
        /*try {
            executorService.shutdown();
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS) && !executorService.isShutdown()) {
                executorService.shutdownNow();
            }
        } catch (Exception throwable) {
            logger.error("ExecutorService shutdown error:{}", throwable.getMessage());
        }*/
        return result;
    }


    /**
     * 记录日志
     *
     * @param joinPoint
     * @param time
     */
    private void recordLog(ProceedingJoinPoint joinPoint, long time, UserDetail userDetail, ServletRequestAttributes attributes) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            SysLog sysLog = new SysLog();
            EnableLog enableLog = method.getAnnotation(EnableLog.class);
            if (null != enableLog) {
                sysLog.setLogType(enableLog.logType().getCode());
                sysLog.setLogContent(enableLog.value());
                sysLog.setOperateType(enableLog.operateType().getCode());
            }
            /**
             * 请求方法名
             */
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            sysLog.setMethod(className + "." + methodName + "()");
            /**
             * 设置操作类型
             */
            if (null == sysLog.getOperateType() || LogTypeEnum.LOG_TYPE_2.getCode() == sysLog.getOperateType()) {
                sysLog.setOperateType(getOperateType(methodName, sysLog.getLogType()));
            }
            /**
             * 请求的方法参数
             */
            Object[] args = joinPoint.getArgs();
            LocalVariableTableParameterNameDiscoverer lvpnd = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = lvpnd.getParameterNames(method);
            if (args != null && parameterNames != null) {
                StringBuilder params = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    params.append("  ").append(parameterNames[i]).append(": ").append(args[i]);
                }
                sysLog.setRequestParam(params.toString());
            }
            sysLog.setIp(IPUtils.getIpAddr(attributes.getRequest()));
            sysLog.setRequestUrl(attributes.getRequest().getRequestURI());
            sysLog.setCostTime(time);
            if (null != userDetail) {
                sysLog.setUserId(userDetail.getUserId());
                sysLog.setUserName(userDetail.getName());
            }
            logFeignClient.record(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("recordLog error:{}", e.getMessage());
        }
    }

    /**
     * 获取操作类型
     */
    private int getOperateType(String methodName, int operateType) {
        if (operateType > 0) {
            return operateType;
        }
        if (methodName.startsWith("list") || methodName.startsWith("query")) {
            return LogOperateTypeEnum.OPERATE_TYPE_1.getCode();
        }
        if (methodName.startsWith("add")) {
            return LogOperateTypeEnum.OPERATE_TYPE_2.getCode();
        }
        if (methodName.startsWith("edit")) {
            return LogOperateTypeEnum.OPERATE_TYPE_3.getCode();
        }
        if (methodName.startsWith("delete")) {
            return LogOperateTypeEnum.OPERATE_TYPE_4.getCode();
        }
        if (methodName.startsWith("import")) {
            return LogOperateTypeEnum.OPERATE_TYPE_5.getCode();
        }
        if (methodName.startsWith("export")) {
            return LogOperateTypeEnum.OPERATE_TYPE_6.getCode();
        }
        return LogOperateTypeEnum.LOG_TYPE_00.getCode();
    }
}
