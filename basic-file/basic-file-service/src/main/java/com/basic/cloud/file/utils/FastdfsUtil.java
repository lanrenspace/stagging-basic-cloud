package com.basic.cloud.file.utils;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class FastdfsUtil {

    public static final String DEFAULT_CHARSET = "UTF-8";

    private FastFileStorageClient storageClient;

    public FastdfsUtil(FastFileStorageClient storageClient) {
        this.storageClient = storageClient;
    }

    /**
     * 上传
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public StorePath upload(MultipartFile multipartFile) throws IOException {
        Set<MetaData> metaData = new HashSet<>();
        metaData.add(new MetaData("author", "fastdfs"));
        metaData.add(new MetaData("description", multipartFile.getOriginalFilename()));
        return storageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), metaData);
    }

    /**
     * 删除
     *
     * @param path
     */
    public void delete(String path) {
        storageClient.deleteFile(path);
    }

    /**
     * 删除
     *
     * @param group
     * @param path
     */
    public void delete(String group, String path) {
        storageClient.deleteFile(group, path);
    }


    /**
     * 下载
     *
     * @param path
     * @param fileName
     * @param response
     * @throws IOException
     */
    public void download(String path, String fileName, HttpServletResponse response) throws IOException {
        StorePath storePath = StorePath.parseFromUrl(path);
        if (StringUtils.isBlank(fileName)) {
            fileName = FilenameUtils.getName(storePath.getPath());
        }
        byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        response.reset();
        response.setContentType("applicatoin/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        out.close();
    }
}
