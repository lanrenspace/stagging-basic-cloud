package com.basic.cloud.file.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.SecurityUser;
import com.basic.cloud.common.boot.PlatformProperties;
import com.basic.cloud.common.contstant.StorageShape;
import com.basic.cloud.common.exceptions.TripartiteServiceException;
import com.basic.cloud.file.dto.FileInfoDTO;
import com.basic.cloud.file.dto.FileShardingInfoDTO;
import com.basic.cloud.file.entity.FileInfo;
import com.basic.cloud.file.entity.FileSharding;
import com.basic.cloud.file.mapper.FileInfoMapper;
import com.basic.cloud.file.service.FileInfoService;
import com.basic.cloud.file.service.FileShardingService;
import com.basic.cloud.file.utils.FastdfsUtil;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class FileInfoServiceImpl extends BaseBeanServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    private final PlatformProperties properties;
    private final FileShardingService fileShardingService;

    /**
     * oss 连接客户端
     */
    private OSS ossClient;

    /**
     * fsdf 工具
     */
    private final FastdfsUtil fastdfsUtil;

    public FileInfoServiceImpl(PlatformProperties properties, FileShardingService fileShardingService, FastdfsUtil fastdfsUtil) {
        this.properties = properties;
        this.fileShardingService = fileShardingService;
        this.fastdfsUtil = fastdfsUtil;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (this.ossClient == null && StorageShape.OSS_STORAGE.equals(this.properties.getFileInfo().getStorageShape())) {
            this.ossClient = new OSSClientBuilder().build(properties.getAliOss().getEndpoint(),
                    properties.getAliOss().getAccessKey(), properties.getAliOss().getAccessSecret());
        }
    }

    @Override
    public FileInfo upload(MultipartFile multipartFile) throws IOException {
        String storageShape = properties.getFileInfo().getStorageShape();
        if (StorageShape.LOCAL_STORAGE.equals(storageShape)) {
            return this.localStorage(multipartFile);
        } else if (StorageShape.OSS_STORAGE.equals(storageShape)) {
            return this.ossStorage(multipartFile);
        } else if (StorageShape.FAST_DFS_STORAGE.equals(storageShape)) {
            return this.fdfsStorage(multipartFile);
        }
        return null;
    }

    @Override
    public FileInfo upload(FileInfoDTO fileInfoDTO, InputStream inputStream) throws IOException {
        String storagePath = properties.getFileInfo().getStorageShape();
        try {
            if (StorageShape.FAST_DFS_STORAGE.equals(storagePath)) {
                return this.fdfsStorage(inputStream, fileInfoDTO);
            } else if (StorageShape.LOCAL_STORAGE.equals(storagePath)) {
                return this.localStorage(inputStream, fileInfoDTO);
            }
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
        return new FileInfo();
    }

    /**
     * 合并文件
     *
     * @param fileInfoDTO
     * @param userInfo
     * @throws IOException
     */
    @Async("fileMergeTaskExecutor")
    @Override
    public void mergeFileAsync(FileShardingInfoDTO fileInfoDTO, SecurityUser userInfo) throws IOException {
        Long shardTotal = fileInfoDTO.getShardTotal();
        File newFile = new File(properties.getFileInfo().getStoragePath() + fileInfoDTO.getFileName());
        if (newFile.exists()) {
            newFile.delete();
        }
        // 文件以追加的方式写入
        FileOutputStream outputStream = new FileOutputStream(newFile, true);
        // 分片文件
        FileInputStream fileInputStream = null;
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;
        try {
            for (int i = 0; i < shardTotal; i++) {
                // 读取第i个分片
                fileInputStream = new FileInputStream(new File(properties.getFileInfo().getStoragePath() + fileInfoDTO.getKey() + "." + (i + 1)));
                while ((len = fileInputStream.read(byt)) != -1) {
                    //一直追加到合并的新文件中
                    outputStream.write(byt, 0, len);
                }
            }
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            outputStream.close();
            System.gc();
        }
        FileInfo fileInfo = getById(fileInfoDTO.getFileId());
        fileInfo.setStoragePath(newFile.getAbsolutePath());
        updateById(fileInfo);
        // 删除分片信息
        FileSharding fileSharding = fileShardingService.lambdaQuery().eq(FileSharding::getShardKey, fileInfoDTO.getKey()).list().get(0);
        for (int i = 1; i <= fileSharding.getTotal(); i++) {
            new File(properties.getFileInfo().getStoragePath() + fileInfoDTO.getKey() + "." + i).deleteOnExit();
        }
        logger.info("文件{},分片合并完毕!", fileInfoDTO.getFileName());
    }

    @Override
    public File getUploadPath() throws FileNotFoundException {
        File fullDir = null;
        if (ObjectUtils.isEmpty(properties.getFileInfo().getStoragePath())) {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            fullDir = new File(path.getAbsolutePath(), "static/upload/");
            if (!fullDir.exists()) {
                fullDir.mkdirs();
            }
        } else {
            fullDir = new File(properties.getFileInfo().getStoragePath());
            if (!fullDir.exists()) {
                fullDir.mkdir();
            }
        }
        return fullDir;
    }

    @Override
    public boolean checkFileSuffix(MultipartFile multipartFile, String fileName) {
        String uploadSuffix;
        if (StringUtils.isEmpty(fileName)) {
            uploadSuffix = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        } else {
            uploadSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        List<String> suffix = Arrays.asList(properties.getFileInfo().getSuffix().split(","));
        if (!suffix.contains(uploadSuffix.toUpperCase()) && !suffix.contains(uploadSuffix.toLowerCase())) {
            return false;
        }
        return true;
    }

    /**
     * 本地存储
     *
     * @param multipartFile
     * @return
     */
    private FileInfo localStorage(MultipartFile multipartFile) throws IOException {
        File fullDir = this.getUploadPath();
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        String fileName = this.appendFileNamePrefix(originalFilename);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(Long.parseLong(getIdStrategy().id()));
        fileInfo.setOriginalName(originalFilename);
        fileInfo.setFileName(fileName);
        fileInfo.setSize(multipartFile.getSize());
        fileInfo.setStorageServer(properties.getFileInfo().getServerPath() + fileInfo.getId());
        fileInfo.setStoragePath(fullDir.getAbsolutePath() + File.separator + fileName);
        try (BufferedOutputStream outputStream = new BufferedOutputStream(
                Files.newOutputStream(new File(fileInfo.getStoragePath()).toPath()))) {
            outputStream.write(multipartFile.getBytes());
            outputStream.flush();
        }
        getBaseMapper().insert(fileInfo);
        return fileInfo;
    }

    /**
     * 本地存储
     *
     * @param inputStream
     * @param fileInfoDTO
     * @return
     */
    private FileInfo localStorage(InputStream inputStream, FileInfoDTO fileInfoDTO) throws IOException {
        File fullDir = this.getUploadPath();
        FileInfo fileInfo = this.constructionFileInfo(fileInfoDTO);
        fileInfo.setStorageServer(properties.getFileInfo().getServerPath() + fileInfo.getId());
        fileInfo.setStoragePath(fullDir.getAbsolutePath() + File.separator + fileInfo.getFileName());
        try (BufferedOutputStream outputStream = new BufferedOutputStream(
                Files.newOutputStream(new File(fileInfo.getStoragePath()).toPath()))) {
            byte[] bytes = new byte[inputStream.available()];
            int read = inputStream.read(bytes);
            fileInfo.setSize((long) bytes.length);
            outputStream.write(bytes);
            outputStream.flush();
        }
        getBaseMapper().insert(fileInfo);
        return fileInfo;
    }


    /**
     * 构造fileInfo对象
     *
     * @param fileInfoDTO
     * @return
     */
    private FileInfo constructionFileInfo(FileInfoDTO fileInfoDTO) {
        String fileName = this.appendFileNamePrefix(fileInfoDTO.getFileName());
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(fileName);
        fileInfo.setOriginalName(fileInfoDTO.getFileName());
        fileInfo.setId(Long.parseLong(getIdStrategy().id()));
        fileInfo.setSize(fileInfo.getSize());
        fileInfo.setCategory(fileInfoDTO.getCategory());
        fileInfo.setRemark(fileInfo.getRemark());
        return fileInfo;
    }

    /**
     * OSS存储
     *
     * @param multipartFile
     * @return
     */
    private FileInfo ossStorage(MultipartFile multipartFile) throws IOException {
        String fileName = this.appendFileNamePrefix(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        PutObjectResult objectResult = ossClient.putObject(properties.getAliOss().getBucketName(),
                fileName, multipartFile.getInputStream());
        if (null == objectResult) {
            logger.error("文件：" + multipartFile.getOriginalFilename() + " 上传失败!");
            throw new TripartiteServiceException("文件上传失败!");
        }
        // 生成签名链接
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(properties.getAliOss().getBucketName(), fileName);
        generatePresignedUrlRequest.setExpiration(new Date(System.currentTimeMillis() + 3600L * 1000L * 24L * 365L * 100L));
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(Long.parseLong(getIdStrategy().id()));
        fileInfo.setOriginalName(multipartFile.getOriginalFilename());
        fileInfo.setFileName(fileName);
        fileInfo.setSize(multipartFile.getSize());
        fileInfo.setStorageServer(url.toString());
        fileInfo.setStoragePath(properties.getAliOss().getBucketName());
        getBaseMapper().insert(fileInfo);
        return fileInfo;
    }

    /**
     * fdfs 存储
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    private FileInfo fdfsStorage(MultipartFile multipartFile) throws IOException {
        StorePath upload = fastdfsUtil.upload(multipartFile);
        String fileName = this.appendFileNamePrefix(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(Long.parseLong(getIdStrategy().id()));
        fileInfo.setOriginalName(multipartFile.getOriginalFilename());
        fileInfo.setFileName(fileName);
        fileInfo.setSize(multipartFile.getSize());
        fileInfo.setStorageServer(properties.getFileInfo().getServerPath());
        fileInfo.setStoragePath(upload.getPath());
        fileInfo.setPath(fileInfo.getStorageServer() + "/" + upload.getFullPath());
        fileInfo.setGroupName(upload.getGroup());
        getBaseMapper().insert(fileInfo);
        return fileInfo;
    }

    /**
     * fdfs 存储
     *
     * @param stream      输入流
     * @param fileInfoDTO 文件基本信息
     * @return
     */
    private FileInfo fdfsStorage(InputStream stream, FileInfoDTO fileInfoDTO) throws IOException {
        FileInfo fileInfo = this.constructionFileInfo(fileInfoDTO);
        StorePath upload = fastdfsUtil.upload(stream, fileInfo.getFileName());
        fileInfo.setStorageServer(properties.getFileInfo().getServerPath());
        fileInfo.setStoragePath(upload.getPath());
        fileInfo.setPath(fileInfo.getStorageServer() + "/" + upload.getFullPath());
        fileInfo.setGroupName(upload.getGroup());
        getBaseMapper().insert(fileInfo);
        return fileInfo;
    }

    /**
     * 追加文件名前缀
     *
     * @param fileName
     * @return
     */
    private String appendFileNamePrefix(String fileName) {
        int index = fileName.lastIndexOf(".");
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowTimeStr = simpleDateFormat.format(new Date());
        fileName = fileName.substring(0, index) + "_" + nowTimeStr + fileName.substring(index);
        return fileName;
    }
}
