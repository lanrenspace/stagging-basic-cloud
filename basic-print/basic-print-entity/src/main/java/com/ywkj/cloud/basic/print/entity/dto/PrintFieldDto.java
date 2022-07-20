package com.ywkj.cloud.basic.print.entity.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "打印字段")
public class PrintFieldDto<T> {

    @ApiModelProperty(value = "字段名")
    private String fieldName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "值对应的类")
    private String className;

    @ApiModelProperty(value = "值")
    private T val;
}
