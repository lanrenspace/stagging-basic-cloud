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
@TableName("uum_user_unit")
public class UserUnit extends BisDataEntity<UserUnit> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 机构部门ID
     */
    private Long unitId;

    /**
     * 是否主部门
     */
    private Boolean main;

    /**
     * 是否有效
     */
    private Boolean valid;

    /**
     * 多部门情况时的先后排序
     */
    private Integer orderNum;

}
