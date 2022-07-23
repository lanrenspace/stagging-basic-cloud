package com.basic.cloud.file.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class ByteReqDTO extends FileInfoDTO {

    /**
     * 字节数组
     */
    @NotNull
    private byte[] bytes;
}
