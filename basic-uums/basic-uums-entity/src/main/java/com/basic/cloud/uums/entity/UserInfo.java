package com.basic.cloud.uums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@TableName("uum_user_info")
public class UserInfo extends BisDataEntity<UserInfo> {

    /**
     * 用户ID
     */
    @TableId
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String slat;

    /**
     * 用户编号/工号
     */
    private String userNo;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * WX Open Id
     */
    private String wxOpenId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 用户头像缩略图
     */
    private String userImageThumbnail;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 昵称
     */
    private String nickName;


    /**
     * 三方编码（公司编码）
     */
    private String thirdNo;

    /**
     * 性别
     */
    private Integer sex;

}
