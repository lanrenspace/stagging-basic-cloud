package com.ywkj.clound.basic.print.api;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.dto.PrintDataDto;
import com.ywkj.cloud.basic.print.dto.merge.MergeFileReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "${api.feign.client.uum}", path = "/basic/feign/print", contextId = "printFeignClient")
public interface PrintFeignClient {

    /**
     * 根据jasperreport生成pdf
     *
     * @return
     */
    @PostMapping("/pdf")
    ResultData<FileInfoVO> pdf(PrintDataDto printDataDto);

    /**
     * 合并文件到一个pdf中
     *
     * @return
     */
    @PostMapping("/merge")
    ResultData<FileInfoVO> merge(MergeFileReqDto mergeFileReqDto);
}
