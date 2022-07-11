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
@TableName("uum_menu_info")
public class MenuInfo extends BisDataEntity<MenuInfo> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父级菜单ID
     */
    private Long parentMenuId;

    /**
     * 菜单类型 1业务菜单,0管理菜单
     */
    private Integer menuType;

    /**
     * 是否页面资源  boolean Y：是，N：否，默认N
     */
    private Boolean pagePoint;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 打开模式  1.工作区打开 2新窗口打开
     */
    private Integer menuOpenMode;

    /**
     * 菜单图标样式
     */
    private String menuImageUrl;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否菜单叶子节点  boolean
     */
    private Boolean menuChief;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 是否加载业务菜单,Y 加载，N 不加载
     */
    private Boolean loading;

}
