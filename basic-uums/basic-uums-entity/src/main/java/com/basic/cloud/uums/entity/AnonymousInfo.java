package com.basic.cloud.uums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 白名单配置信息
 **/
@Data
@TableName("uum_anonymous_info")
public class AnonymousInfo extends BisDataEntity<AnonymousInfo> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 应用服务ID
     */
    private String appId;

    /**
     * 资源路由
     */
    private String url;

    /**
     * 请求方式
     */
    private String httpMethod;

    /**
     * 描述
     */
    private String description;
}
