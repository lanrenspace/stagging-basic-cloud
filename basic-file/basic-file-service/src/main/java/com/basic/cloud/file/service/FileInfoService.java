package com.basic.cloud.file.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.common.bean.SecurityUser;
import com.basic.cloud.file.entity.FileInfo;
import com.basic.cloud.file.dto.FileInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface FileInfoService extends IBaseBeanService<FileInfo> {

    /**
     * 上传
     * @param multipartFile
     * @return
     */
    FileInfo upload(MultipartFile multipartFile) throws IOException;

    /**
     * 合并文件
     *
     * @param fileInfoDTO
     */
    void mergeFileAsync(FileInfoDTO fileInfoDTO, SecurityUser userInfo) throws IOException;

    /**
     * 获取文件上传路径
     *
     * @return
     * @throws FileNotFoundException
     */
    File getUploadPath() throws FileNotFoundException;

    /**
     * 校验文件格式
     *
     * @param multipartFile
     * @param fileName
     * @return
     */
    boolean checkFileSuffix(MultipartFile multipartFile, String fileName);
}
