package com.basic.cloud.file.api;

import com.basic.cloud.boot.MultipartSupportConfig;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.dto.ByteReqDTO;
import com.basic.cloud.file.vo.FileInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.file}", path = "/feign/fileInfo", contextId = "fileInfoFeignClient", configuration = MultipartSupportConfig.class)
public interface FileInfoFeignClient {

    /**
     * 字节数组上传
     *
     * @param reqDTO
     * @return
     */
    @PostMapping("/byteUpload")
    ResultData<FileInfoVO> byteUpload(@RequestBody ByteReqDTO reqDTO);
}
