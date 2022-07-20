package com.basic.cloud.gateway.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lanrenspace@163.com
 * @Description: 网关日志
 **/
@Data
public class GatewayLog implements Serializable {

    /**
     * 服务实例
     */
    private String server;

    /**
     * 请求url
     */
    private String path;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 协议
     */
    private String schema;

    /**
     * 请求体
     */
    private String reqBody;

    /**
     * 响应体
     */
    private String rspBody;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 请求时间
     */
    private Date reqDate;

    /**
     * 响应时间
     */
    private Date rspDate;

    /**
     * 执行时间
     */
    private Long executeTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;
}
