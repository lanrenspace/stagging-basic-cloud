package com.basic.cloud.uums.dto;

import com.basic.cloud.common.annotion.MappingData;
import com.basic.cloud.common.annotion.MappingField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@MappingData
public class BlackIpsDTO implements Serializable {

    /**
     * ip
     */
    @NotBlank
    @NotNull
    @MappingField
    private String ip;

    /**
     * 禁用时间
     */
    @MappingField(targetClass = Date.class)
    private Long disabledDate;

    /**
     * 截止时间
     */
    @MappingField(targetClass = Date.class)
    private Long deadlineDate;
}
