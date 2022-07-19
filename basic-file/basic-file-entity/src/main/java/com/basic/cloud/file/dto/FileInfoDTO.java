package com.basic.cloud.file.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class FileInfoDTO implements Serializable {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 业务目录
     */
    private String category;

    /**
     * 备注
     */
    private String remark;

    /**
     * 文件大小
     */
    private Long size;
}
