package com.ywkj.cloud.basic.print.controller;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.dto.PrintDataDto;
import com.ywkj.cloud.basic.print.dto.merge.MergeFileReqDto;
import com.ywkj.cloud.basic.print.service.MergeFileService;
import com.ywkj.cloud.basic.print.service.PrintService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/basic/feign/print")
public class PrintController {

    private PrintService printService;
    private MergeFileService mergeFileService;

    @ApiOperation(value = "生成pdf打印")
    @PostMapping("/pdf")
    public ResultData<FileInfoVO> pdf(@RequestBody @Validated PrintDataDto printDataDto) throws Exception {
        return ResultData.ok(printService.printPdf(printDataDto));
    }

    @ApiOperation(value = "合并后打印")
    @PostMapping("/merge")
    public ResultData<FileInfoVO> merge(@RequestBody @Validated MergeFileReqDto mergeFileReqDto) throws Exception {
        return ResultData.ok(mergeFileService.mergeFile(mergeFileReqDto));
    }
}
