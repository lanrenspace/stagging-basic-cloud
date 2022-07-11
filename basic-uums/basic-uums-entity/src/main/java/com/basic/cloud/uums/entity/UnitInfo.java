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
@TableName("uum_unit_info")
public class UnitInfo extends BisDataEntity<UnitInfo> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 机构编码
     */
    private String unitCode;

    /**
     * 机构名称
     */
    private String unitName;

    /**
     * 上级组织机构编码
     */
    private Long parentUnitId;

    /**
     * 组织机构类型，1组织机构，2部门，3分公司
     */
    private String unitType;

    /**
     * 排序号，越小越好
     */
    private Integer orderNum;

    /**
     * 是否有效
     */
    private Boolean valid;

    /**
     * 机构全路径名称
     */
    private String unitFullName;

    /**
     * 下级租户编号，下级机构编码
     */
    private String subTenantCode;

    /**
     * 启用日期
     */
    private Date enabledDate;

    /**
     * 树结构处理，树路径
     */
    private String treePath;

    /**
     * 组织管理员账号
     */
    private String adminAccount;

}
