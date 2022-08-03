package com.basic.cloud.common.starter;

import com.basic.cloud.common.base.AutoConfigurationConditional;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.common.vo.business.UrlResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@ConditionalOnMissingBean(value = {AutoConfigurationConditional.class})
@RestController("/platform")
@Configuration
public class BusinessAutoConfiguration {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CommonAutoConfiguration.class);

    /**
     * 系统请求路径
     */
    private final String PLATFORM_URL_PATH = "/platform";

    /**
     * 资源url初始化加载请求路径
     */
    private final String LOAD_RESOURCE_URL_PATH = "/init-load-resource-url";

    private final WebApplicationContext applicationContext;

    public BusinessAutoConfiguration(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        logger.info("load Business comp success!");
    }

    /**
     * 初始化加载服务所有url资源信息
     * 避免手动配置导致出错率高
     *
     * @return
     */
    @GetMapping(LOAD_RESOURCE_URL_PATH)
    public ResultData<List<UrlResVO>> loadResourceUrls() {
        RequestMappingHandlerMapping mapping = this.applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        List<UrlResVO> urlList = new ArrayList<>();
        for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            patterns = patterns.stream().filter(url -> !url.equals(PLATFORM_URL_PATH + LOAD_RESOURCE_URL_PATH)).collect(Collectors.toSet());
            Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
            if (CollectionUtils.isEmpty(patterns) || CollectionUtils.isEmpty(methods)) {
                break;
            }
            for (String url : patterns) {
                for (RequestMethod method : methods) {
                    urlList.add(new UrlResVO(url, method.name()));
                }
            }
        }
        return ResultData.ok(urlList);
    }
}
