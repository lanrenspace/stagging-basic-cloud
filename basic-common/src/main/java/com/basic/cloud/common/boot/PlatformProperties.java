package com.basic.cloud.common.boot;

import com.basic.cloud.common.contstant.FileType;
import com.basic.cloud.common.contstant.StorageShape;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "platform")
public class PlatformProperties {

    /**
     * 自定义环境常量
     */
    private List<String> activeEnvs = Arrays.asList(ActiveEnv.DEV, ActiveEnv.TEST, ActiveEnv.SIT, ActiveEnv.PROD);

    /**
     * swagger 配置信息
     */
    private List<Swagger> swagger;

    /**
     * 文件服务配置
     */
    private FileInfo fileInfo = new FileInfo();

    /**
     * aliOss配置信息
     */
    private AliOss aliOss = new AliOss();

    /**
     * jwt 配置信息
     */
    private Jwt jwt = new Jwt();

    /**
     * 系统环境
     */
    @Data
    public static class ActiveEnv {

        /**
         * 开发环境
         */
        public static String DEV = "dev";

        /**
         * 测试环境
         */
        public static String TEST = "test";

        /**
         * 集成测试环境
         */
        public static String SIT = "sit";

        /**
         * 生产环境
         */
        public static String PROD = "prod";
    }


    /**
     * swagger
     */
    @Data
    public static class Swagger {
        /**
         * groupName
         */
        private String groupName;

        /**
         * 扫描包路径
         */
        private String basePackage;

        /**
         * 文档信息
         */
        private ApiInfo apiInfo;

        @Data
        public static class ApiInfo {

            /**
             * 文档标题
             */
            private String title;

            /**
             * 文档描述
             */
            private String description;

            /**
             * 文档版本
             */
            private String version;

            /**
             * 服务条款URL
             */
            private String termsOfServiceUrl;

        }
    }

    /**
     * 文件服务配置
     */
    @Data
    public static class FileInfo {

        /**
         * 存储路径
         */
        private String storagePath;

        /**
         * 格式
         */
        private String suffix = FileType.suffixList();

        /**
         * 服务请求路径
         */
        private String serverPath;

        /**
         * 存储形式
         */
        private String storageShape = StorageShape.LOCAL_STORAGE;

    }

    /**
     * 阿里云OSS配置
     */
    @Data
    public static class AliOss {

        /**
         * key
         */
        private String accessKey;


        /**
         * secret
         */
        private String accessSecret;


        /**
         * 容器名称
         */
        private String bucketName;

        /**
         * 端点域名
         */
        private String endpoint;
    }

    /**
     * OAuth2 Jwt相关配置
     */
    @Data
    public static class Jwt {
        /**
         * access token 有效时长
         */
        private Integer accessTokenValiditySeconds = 86400 * 3;

        /**
         * refresh token 有效时长
         * 系统使用：refreshTokenValiditySeconds * (86400 * 3)
         */
        private Integer refreshTokenValiditySeconds = 86400 * 4;
    }
}
