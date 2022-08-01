package com.basic.cloud.log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 系统数据日志
 **/
@Data
@TableName("sys_business_data_log")
public class SysDataLog extends BisDataEntity<SysDataLog> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 数据表名称
     */
    private String tableName;

    /**
     * 数据ID
     */
    private Long dataId;

    /**
     * 内容
     */
    private String content;

    /**
     * 版本
     */
    private Integer version;
}
