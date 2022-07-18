package com.basic.cloud.file.vo;

import com.basic.cloud.common.annotion.MappingData;
import com.basic.cloud.common.annotion.MappingField;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 文件主信息
 **/
@Data
@MappingData
public class FileInfoVO {

    /**
     * 文件ID
     */
    @MappingField
    private Long id;

    /**
     * 文件名
     */
    @MappingField
    private String fileName;

    /**
     * 原名
     */
    @MappingField
    private String originalName;

    /**
     * 存储路径
     */
    @MappingField
    private String storagePath;

    /**
     * 存储服务
     */
    @MappingField
    private String storageServer;

    /**
     * 文件大小
     */
    @MappingField
    private Long size;

    /**
     * 业务目录
     */
    @MappingField
    private String category;

    /**
     * 分组名称
     */
    @MappingField
    private String groupName;

    /**
     * 完整路径
     */
    @MappingField
    private String path;

    /**
     * 文件备注
     */
    @MappingField
    private String remark;

    /**
     * 排序
     */
    @MappingField
    private Integer sort;
}
