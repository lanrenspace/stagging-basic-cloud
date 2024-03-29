package com.basic.cloud.uums.dto;

import com.basic.cloud.uums.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author huji
 * @Date 2022-08-01
 */
@Data
@ApiModel("最简单的添加用户")
public class AddUserReqQuickDto implements Serializable {

    /**
     * 名称
     */
    @NotNull(message = "用户姓名不能为空")
    @ApiModelProperty(value = "用户姓名")
    private String name;

    /**
     * 电话
     */
    @NotNull(message = "用户手机号不能为空")
    @ApiModelProperty(value = "用户手机号")
    private String mobile;

    /**
     * 租户编码
     */
    @NotNull
    @ApiModelProperty(value = "租户编码")
    protected String tenantCode;
}
