package com.basic.cloud.uums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author lanrenspace@163.com
 * @Description: 黑名单IP
 **/
@Data
@TableName("authority_black_ip")
public class BlackIps extends BisDataEntity<BlackIps> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 禁用时间
     */
    private Date disabledDate;

    /**
     * 截止时间 为null则永久禁用
     */
    private Date deadlineDate;
}
