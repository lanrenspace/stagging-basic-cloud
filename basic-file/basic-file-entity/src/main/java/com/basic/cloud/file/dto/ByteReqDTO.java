package com.basic.cloud.file.dto;

import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class ByteReqDTO extends FileInfoDTO {

    /**
     * 字节数组
     */
    private byte[] bytes;
}
