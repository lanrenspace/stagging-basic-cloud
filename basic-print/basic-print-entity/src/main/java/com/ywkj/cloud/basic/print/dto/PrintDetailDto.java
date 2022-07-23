package com.ywkj.cloud.basic.print.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "打印字段(详情)")
public class PrintDetailDto {

    @ApiModelProperty(value = "详情因为有多行数据，所以是一个列表集合")
    List<PrintFieldDto> fields;
}
