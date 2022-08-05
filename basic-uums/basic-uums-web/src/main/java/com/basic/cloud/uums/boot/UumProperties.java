package com.basic.cloud.uums.boot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@Component
@ConfigurationProperties(prefix = "platform")
public class UumProperties {

    /**
     * 系统相关配置
     */
    private System system = new System();

    /**
     * 系统相关
     */
    @Data
    public static class System {

        /**
         * 资源URL实时权限校验
         */
        private boolean resUrlTimelyPermission = true;
    }
}
