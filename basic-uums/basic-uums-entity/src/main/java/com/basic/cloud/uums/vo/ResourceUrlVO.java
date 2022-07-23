package com.basic.cloud.uums.vo;

import com.basic.cloud.common.annotion.MappingData;
import com.basic.cloud.common.annotion.MappingField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@MappingData
@Data
public class ResourceUrlVO implements Serializable {

    /**
     * 应用服务ID
     */
    @MappingField
    private String appId;

    /**
     * 资源名称
     */
    @MappingField
    private String name;

    /**
     * 资源url
     */
    @MappingField
    private String url;

    /**
     * httpMethod
     */
    @MappingField
    private String method;

    public ResourceUrlVO(String appId, String name, String url, String method) {
        this.appId = appId;
        this.name = name;
        this.url = url;
        this.method = method;
    }

    public ResourceUrlVO() {

    }
}
