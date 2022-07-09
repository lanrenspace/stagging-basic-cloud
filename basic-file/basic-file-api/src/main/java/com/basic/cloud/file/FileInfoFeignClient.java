package com.basic.cloud.file;

import com.basic.cloud.common.vo.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${file.api.feign.client}", path = "feignClient/file")
public interface FileInfoFeignClient {

    /**
     * 文件上传
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/file/fullUpload")
    ResultData upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException;

}
