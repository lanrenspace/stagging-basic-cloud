package com.basic.cloud.uums.vo;

import com.basic.cloud.common.annotion.MappingData;
import com.basic.cloud.common.annotion.MappingField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@MappingData
public class BlackIpVO implements Serializable {

    /**
     * ip地址
     */
    @MappingField
    private String ip;

    /**
     * 截止时间 为null则永久禁用
     */
    @MappingField
    private Date deadlineDate;
}
