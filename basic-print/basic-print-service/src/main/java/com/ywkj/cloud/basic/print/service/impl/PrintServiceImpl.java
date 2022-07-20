package com.ywkj.cloud.basic.print.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.file.api.FileInfoFeignClient;
import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.entity.dto.PrintDataDto;
import com.ywkj.cloud.basic.print.entity.entity.PrintRecord;
import com.ywkj.cloud.basic.print.mapper.PrintMapper;
import com.ywkj.cloud.basic.print.service.PrintService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PrintServiceImpl extends BaseBeanServiceImpl<PrintMapper, PrintRecord> implements PrintService {

    @Resource
    FileInfoFeignClient fileInfoFeignClient;

    @Override
    public FileInfoVO printPdf(PrintDataDto printDataDto) {
        FileInfoVO fileInfoVO = new FileInfoVO();


        fileInfoFeignClient.byteUpload();
        return fileInfoVO;
    }
}
