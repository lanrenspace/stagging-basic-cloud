package com.basic.cloud.common.utils;

import com.basic.cloud.common.boot.PlatformProperties;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class AppContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextHelper.applicationContext = applicationContext;
    }

    /**
     * 获取上下文对象
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> cla) {
        return getApplicationContext().getBean(cla);
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static Object getComponent(String name) {
        Object obj = null;
        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext.containsBean(name)) {
            obj = applicationContext.getBean(name);
        } else {
            return null;
        }
        return obj;
    }

    /**
     * 获取当前运行环境
     *
     * @return
     */
    public static String getActiveProfile() {
        String[] activeProfiles = getApplicationContext().getEnvironment().getActiveProfiles();
        if (activeProfiles.length < 1) {
            return PlatformProperties.ActiveEnv.DEV;
        }
        return activeProfiles[0];
    }
}
