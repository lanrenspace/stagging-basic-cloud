package com.basic.cloud.file.vo;

import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 文件主信息
 **/
@Data
public class FileInfoVO {

    /**
     * 文件ID
     */
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原名
     */
    private String originalName;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 存储服务
     */
    private String storageServer;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 业务目录
     */
    private String category;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 完整路径
     */
    private String path;

    /**
     * 文件备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;
}
