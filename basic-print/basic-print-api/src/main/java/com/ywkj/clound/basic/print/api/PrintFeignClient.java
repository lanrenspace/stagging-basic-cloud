package com.ywkj.clound.basic.print.api;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.dto.PrintDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "${api.feign.client.print}", path = "/basic/feign/print", contextId = "printFeignClient")
public interface PrintFeignClient {

    /**
     * 获取所有的白名单信息
     *
     * @return
     */
    @PostMapping("/pdf")
    ResultData<FileInfoVO> pdf(PrintDataDto printDataDto);
}
