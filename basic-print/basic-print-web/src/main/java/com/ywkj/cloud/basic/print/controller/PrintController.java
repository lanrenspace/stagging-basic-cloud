package com.ywkj.cloud.basic.print.controller;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.dto.PrintDataDto;
import com.ywkj.cloud.basic.print.service.PrintService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/basic/feign/print")
public class PrintController {

    PrintService printService;

    public PrintController(PrintService printService) {
        this.printService = printService;
    }

    @ApiOperation(value = "生成pdf打印")
    @PostMapping("/pdf")
    public ResultData<FileInfoVO> print(@RequestBody @Validated PrintDataDto printDataDto, HttpServletResponse response) throws Exception {
        return ResultData.ok(printService.printPdf(printDataDto, response));
    }
}
