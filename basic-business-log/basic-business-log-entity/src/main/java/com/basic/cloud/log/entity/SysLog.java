package com.basic.cloud.log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 系统业务日志
 **/
@Data
@TableName("sys_business_log")
public class SysLog extends BisDataEntity<SysLog> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 日志类型1：登陆日志2：操作日志
     */
    private Integer logType;

    /**
     * 日志内容
     */
    private String logContent;

    /**
     * 操作类型
     */
    private Integer operateType;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作用户名称
     */
    private String userName;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 操作耗时
     */
    private Long costTime;
}
