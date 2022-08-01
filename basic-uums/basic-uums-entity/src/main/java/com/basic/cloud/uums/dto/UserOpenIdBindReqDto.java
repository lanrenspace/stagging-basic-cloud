package com.basic.cloud.uums.dto;

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
public class UserOpenIdBindReqDto implements Serializable {

    /**
     * 名称
     */
    @NotNull(message = "用户手机号不能为空")
    @ApiModelProperty(value = "用户手机号")
    private String mobile;

    /**
     * 租户编码
     */
    @NotNull
    @ApiModelProperty(value = "微信的openid")
    protected String openId;
}
