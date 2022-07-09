package com.basic.cloud.file.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 文件分片信息
 **/
@Data
@TableName("bis_file_sharding")
public class FileSharding extends BisDataEntity<FileSharding> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 分片编码
     */
    private String shardKey;

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 第几个分片
     */
    private Long shardIndex;

    /**
     * 总分片
     */
    private Long total;
}
