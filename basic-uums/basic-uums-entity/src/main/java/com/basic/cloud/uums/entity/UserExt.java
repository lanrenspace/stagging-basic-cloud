package com.basic.cloud.uums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@TableName("uum_user_ext")
public class UserExt extends BisDataEntity<UserExt> {

    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 初始密码改变日期
     */
    private Date userPasswordChanged;

    /**
     * 账号启用日期
     */
    private Date userEnabledDate;

    /**
     * 失效时间
     */
    private Date userDisabledDate;

    /**
     * 是否锁定
     */
    private Boolean userAccountLocked;

    /**
     * 最后登录日期
     */
    private Date lastLoginDate;

    /**
     * 登录失败次数
     */
    private Integer loginFails;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 用户描述
     */
    private String userDescription;

}
