package com.basic.cloud.common.contstant;

/**
 * 系统常量
 */
public interface SysConst {

    /**
     * 请求用户ID
     */
    String REQ_USER_ID = "req-user-id";

    /**
     * 请求用户账号
     */
    String REQ_USER_ACCOUNT = "req-user-account";

    /**
     * 请求用户IP
     */
    String REQ_USER_IP = "req-user-ip";

    /**
     * token附加参数-用户ID
     */
    String TOKEN_AFFIX_USER_ID = "userId";

    /**
     * token附加参数-登录账号
     */
    String TOKEN_AFFIX_USER_ACCOUNT = "userAccount";

    /**
     * 用户授权缓存key
     */
    String AUTH_LOGIN_CACHE_KEY = CacheDefine.getKey("uaa:auth", "%s");

    /**
     * 匿名用户
     */
    Long ANONYMOUS_USER = 0L;
}
