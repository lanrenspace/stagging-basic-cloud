package com.ywkj.cloud.basic.print.entity;

import com.basic.cloud.common.bean.BisDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("打印模板")
public class PrintTemplateInfo extends BisDataEntity<PrintTemplateInfo> {

    @ApiModelProperty("打印模板编号")
    private String no;

    @ApiModelProperty(value = "模板字段的ID")
    private Long fieldId;

}
