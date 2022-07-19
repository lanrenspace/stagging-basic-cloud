package com.basic.cloud.file.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.boot.PlatformProperties;
import com.basic.cloud.common.contstant.DelEnum;
import com.basic.cloud.common.transfer.ModelMapper;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.entity.FileInfo;
import com.basic.cloud.file.entity.FileSharding;
import com.basic.cloud.file.service.FileInfoService;
import com.basic.cloud.file.service.FileShardingService;
import com.basic.cloud.file.vo.FileInfoVO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Api
@RestController
@RequestMapping("/file")
public class FileInfoController {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(FileInfoController.class);

    private final FileInfoService fileInfoService;
    private final FileShardingService fileShardingService;
    private final PlatformProperties properties;

    public FileInfoController(FileInfoService fileInfoService, FileShardingService fileShardingService, PlatformProperties properties) {
        this.fileInfoService = fileInfoService;
        this.fileShardingService = fileShardingService;
        this.properties = properties;
    }

    /**
     * 全量上传
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/fullUpload")
    public ResultData upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        if (!fileInfoService.checkFileSuffix(multipartFile, "")) {
            return ResultData.error("上传的文件类型不允许!");
        }
        FileInfo fileInfo = fileInfoService.upload(multipartFile);
        if (fileInfo == null) {
            return ResultData.error("上传失败,请稍后重试!");
        }
        FileInfoVO result = new FileInfoVO();
        ModelMapper.map(result, fileInfo);
        return ResultData.ok(result);
    }

    /**
     * 检查文件分片是否已经上传
     *
     * @param key
     * @return
     */
    @GetMapping("/checkShard")
    public ResultData checkShard(@RequestParam String key) {
        logger.info("分片检查：{}", key);
        List<FileSharding> list = fileShardingService.list(Wrappers.lambdaQuery(new FileSharding()).eq(BisDataEntity::getDelFlag, DelEnum.NOT_DELETED.getCode())
                .eq(FileSharding::getShardKey, key));
        return ResultData.ok(CollectionUtils.isEmpty(list) ? -1 : list
                .get(0).getShardIndex());
    }
}
