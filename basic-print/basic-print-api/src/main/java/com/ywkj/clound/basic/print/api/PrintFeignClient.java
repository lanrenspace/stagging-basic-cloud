package com.ywkj.clound.basic.print.api;

import com.ywkj.cloud.basic.print.entity.dto.PrintDataDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${api.feign.client.uum}", path = "/basic/feign/print", contextId = "printFeignClient")
public interface PrintFeignClient {

    String print(PrintDataDto printDataDto);
}
