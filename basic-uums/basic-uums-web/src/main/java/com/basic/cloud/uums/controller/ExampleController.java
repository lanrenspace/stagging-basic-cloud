package com.basic.cloud.uums.controller;

import com.basic.cloud.common.utils.UserUtil;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.api.FileInfoFeignClient;
import com.basic.cloud.file.dto.ByteReqDTO;
import com.basic.cloud.file.dto.FileInfoDTO;
import com.basic.cloud.file.vo.FileInfoVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/example")
public class ExampleController {

    private final FileInfoFeignClient fileInfoFeignClient;

    public ExampleController(FileInfoFeignClient fileInfoFeignClient) {
        this.fileInfoFeignClient = fileInfoFeignClient;
    }

    @GetMapping("/user")
    public ResultData getUser() {
        return ResultData.ok(UserUtil.getUser());
    }

    @PostMapping("/upload")
    public ResultData upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        FileInfoDTO reqDTO = new FileInfoDTO();
        reqDTO.setFileName(multipartFile.getOriginalFilename());
        byte[] bytes = multipartFile.getBytes();
        ByteReqDTO reqDTO1 = new ByteReqDTO();
        reqDTO1.setBytes(multipartFile.getBytes());
        reqDTO1.setFileName(multipartFile.getOriginalFilename());
//        return this.fileInfoFeignClient.streamUpload(reqDTO);
        ResultData<FileInfoVO> fileInfoVOResultData = this.fileInfoFeignClient.byteUpload(reqDTO1);
        return fileInfoVOResultData;
    }
}
