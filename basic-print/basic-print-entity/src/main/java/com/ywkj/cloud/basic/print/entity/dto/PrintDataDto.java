package com.ywkj.cloud.basic.print.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "打印参数")
public class PrintDataDto {

    @ApiModelProperty(value = "打印方案编号")
    @NotNull
    private String templateNo;

    @ApiModelProperty(value = "汇总字段")
    @NotNull
    List<PrintFieldDto> fields;

    @ApiModelProperty(value = "明细字段")
    List<PrintDetailDto> items;
}
