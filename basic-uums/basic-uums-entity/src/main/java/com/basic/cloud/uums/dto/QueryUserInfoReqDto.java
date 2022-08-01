package com.basic.cloud.uums.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.basic.cloud.common.dto.PageDTO;
import com.basic.cloud.uums.entity.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查询用户信息")
public class QueryUserInfoReqDto extends PageDTO {
    /**
     * 名称
     */
    @ApiModelProperty(value = "用户名称")
    private String name;

    /**
     * 电话
     */
    @ApiModelProperty(value = "用户手机号码")
    private String mobile;

    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;
}
