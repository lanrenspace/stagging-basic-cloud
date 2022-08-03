package com.basic.cloud.common.vo.business;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description: Url请求资源
 **/
@Data
public class UrlResVO implements Serializable {

    /**
     * url
     */
    private String url;

    /**
     * 请求方式
     */
    private String method;

    public UrlResVO(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public UrlResVO() {

    }
}
