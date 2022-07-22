package com.basic.cloud.file.feignclient;

import com.basic.cloud.common.transfer.ModelMapper;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.dto.ByteReqDTO;
import com.basic.cloud.file.entity.FileInfo;
import com.basic.cloud.file.service.FileInfoService;
import com.basic.cloud.file.vo.FileInfoVO;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/fileInfo")
public class FileController {

    private final FileInfoService fileInfoService;

    public FileController(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    /**
     * 字节数组上传
     *
     * @param reqDTO
     * @return
     */
    @PostMapping("/byteUpload")
    public ResultData<FileInfoVO> byteUpload(@RequestBody ByteReqDTO reqDTO) {
        if (ObjectUtils.isEmpty(reqDTO.getBytes()) || ObjectUtils.isEmpty(reqDTO.getFileName())) {
            return ResultData.error("上传请求参数不能为空!");
        }
        try {
            FileInfoVO result = new FileInfoVO();
            FileInfo fileInfo = fileInfoService.upload(reqDTO, new ByteArrayInputStream(reqDTO.getBytes()));
            ModelMapper.map(result, fileInfo);
            return ResultData.ok(result);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
    }

    /**
     * 根据主键ID获取文件信息
     *
     * @param fileId
     * @return
     */
    @GetMapping("/getFileByPk")
    public ResultData<FileInfoVO> getFileInfoById(Long fileId) {
        if (ObjectUtils.isEmpty(fileId)) {
            return ResultData.error("请求参数 fileId 不能为空!");
        }
        FileInfo fileInfo = fileInfoService.getById(fileId);
        if (ObjectUtils.isEmpty(fileInfo)) {
            return ResultData.ok();
        }
        FileInfoVO result = new FileInfoVO();
        ModelMapper.map(result, fileInfo);
        return ResultData.ok(result);
    }
}
