package com.basic.cloud.uums.vo;

import com.basic.cloud.common.annotion.MappingData;
import com.basic.cloud.common.annotion.MappingField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@MappingData
public class AnonymousInfoVO implements Serializable {

    /**
     * 应用服务ID
     */
    @MappingField
    private String appId;

    /**
     * 资源路由
     */
    @MappingField
    private String url;

    /**
     * 请求方式
     */
    @MappingField
    private String httpMethod;
}
