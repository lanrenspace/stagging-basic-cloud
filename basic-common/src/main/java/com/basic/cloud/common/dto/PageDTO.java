package com.basic.cloud.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description: 分页请求参数
 **/
@Data
public class PageDTO implements Serializable {

    /**
     * 当前页
     */
    private Integer pageNumber;


    /**
     * 每页大小
     */
    private Integer pageSize;
}
