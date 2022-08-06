package com.basic.cloud.message.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description: 验证码返回对象
 **/
@Data
@Builder
public class VarCodeVO implements Serializable {

    /**
     * 验证码
     */
    private String code;

    /**
     * 有效时长
     */
    private Long second;

    @Tolerate
    public VarCodeVO(){}

}
