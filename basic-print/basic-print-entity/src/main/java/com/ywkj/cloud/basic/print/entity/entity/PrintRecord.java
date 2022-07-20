package com.ywkj.cloud.basic.print.entity.entity;

import com.basic.cloud.common.bean.BisDataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("打印记录")
public class PrintRecord extends BisDataEntity<PrintRecord> {


    private String host;
    private String url;

}
