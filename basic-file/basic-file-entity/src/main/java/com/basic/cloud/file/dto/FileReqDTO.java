package com.basic.cloud.file.dto;

import lombok.Data;

import java.io.File;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class FileReqDTO extends FileInfoDTO {

    /**
     * 文件信息
     */
    private File file;
}
