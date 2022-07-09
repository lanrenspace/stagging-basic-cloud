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
     * 分片key
     */
    private String key;

    /**
     * 分片下标
     */
    private Long shardIndex;

    /**
     * 分片总数
     */
    private Long shardTotal;


    /**
     * 文件名
     */
    private String fileName;


    /**
     * 文件ID
     */
    private Long fileId;
}
