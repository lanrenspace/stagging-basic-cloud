package com.ywkj.cloud.basic.print.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.ywkj.cloud.basic.print.entity.entity.PrintTemplateInfo;
import com.ywkj.cloud.basic.print.mapper.PrintTemplateInfoMapper;
import com.ywkj.cloud.basic.print.service.PrintTemplateInfoService;
import org.springframework.stereotype.Service;

@Service
public class PrintTemplateInfoServiceImpl extends BaseBeanServiceImpl<PrintTemplateInfoMapper, PrintTemplateInfo> implements PrintTemplateInfoService {
}
