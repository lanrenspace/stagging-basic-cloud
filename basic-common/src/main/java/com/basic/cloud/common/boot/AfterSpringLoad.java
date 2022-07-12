package com.basic.cloud.common.boot;

import com.basic.cloud.common.base.IAfterSpringLoadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.*;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class AfterSpringLoad implements ApplicationListener<ApplicationEvent> {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AfterSpringLoad.class);

    /**
     * 是否初始化
     */
    private boolean init = false;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            if (this.init) {
                return;
            }
            logger.info("after spring loading");
            Map<String, IAfterSpringLoadService> beansOfType = ((ContextRefreshedEvent) applicationEvent).getApplicationContext().getBeansOfType(IAfterSpringLoadService.class);
            List<IAfterSpringLoadService> infos = new ArrayList<>(beansOfType.values());
            infos.sort(Comparator.comparingInt(IAfterSpringLoadService::getOrder));
            infos.forEach(afterSpringLoad -> {
                try {
                    logger.info("afterSpringService " + afterSpringLoad.getClass().getName() + " is running.");
                    afterSpringLoad.run();
                    logger.info("afterSpringService " + afterSpringLoad.getClass().getName() + " running end.");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            });
            this.init = true;
        }
    }
}
