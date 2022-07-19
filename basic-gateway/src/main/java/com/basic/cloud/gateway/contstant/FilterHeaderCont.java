package com.basic.cloud.gateway.contstant;

/**
 * @Author lanrenspace@163.com
 * @Description: 过滤器链拦截请求头
 **/
public interface FilterHeaderCont {

    /**
     * 匿名请求头参数
     */
    String ANONYMOUS_REQ_PARAM = "anonymousReq";

    /**
     * 匿名请求头值
     */
    String ANONYMOUS_REQ_VALUE = "anonymousReq_match";
}
