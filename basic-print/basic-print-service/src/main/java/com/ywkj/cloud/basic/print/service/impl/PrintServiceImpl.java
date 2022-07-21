package com.ywkj.cloud.basic.print.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.file.api.FileInfoFeignClient;
import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.entity.dto.PrintDataDto;
import com.ywkj.cloud.basic.print.entity.dto.PrintDetailDto;
import com.ywkj.cloud.basic.print.entity.dto.PrintFieldDto;
import com.ywkj.cloud.basic.print.entity.entity.PrintRecord;
import com.ywkj.cloud.basic.print.entity.entity.PrintTemplateInfo;
import com.ywkj.cloud.basic.print.mapper.PrintMapper;
import com.ywkj.cloud.basic.print.service.PrintService;
import com.ywkj.cloud.basic.print.service.PrintTemplateInfoService;
import com.ywkj.cloud.basic.print.utils.JasperReportUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrintServiceImpl extends BaseBeanServiceImpl<PrintMapper, PrintRecord> implements PrintService {

    @Resource
    PrintTemplateInfoService printTemplateInfoService;
    @Resource
    FileInfoFeignClient fileInfoFeignClient;

    @Override
    public FileInfoVO printPdf(PrintDataDto printDataDto, HttpServletResponse response) throws Exception {
        FileInfoVO fileInfoVO = new FileInfoVO();

        // 获取模板ID
        LambdaQueryWrapper<PrintTemplateInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PrintTemplateInfo::getNo, printDataDto.getTemplateNo());

        // 找到对应的打印方案
        List<PrintTemplateInfo> printTemplateInfos = printTemplateInfoService.list(lqw);
        Assert.isTrue(!CollectionUtils.isEmpty(printTemplateInfos), "找不到对应的打印方案："+printDataDto.getTemplateNo());

        // 查找文件
        FileInfoVO templateFileInfoVo = fileInfoFeignClient.findById(printTemplateInfos.get(0).getFieldId());


        final Map<String, Object> param = new HashMap<>();
        printDataDto.getFields().forEach(field -> {
            param.put(field.getFieldName(), field.getVal());
        });

        List<Map<String, Object>> detail = null;
        if (!CollectionUtils.isEmpty(printDataDto.getItems())) {
            detail = new ArrayList<>();

            Map<String, Object> map = null;

            for (PrintDetailDto detailDto : printDataDto.getItems()) {
                map = new HashMap<>();

                for (PrintFieldDto fieldDto : detailDto.getFields()) {
                    map.put(fieldDto.getFieldName(), fieldDto.getVal());
                }
                detail.add(map);
                map = null;
            }
        }

        JasperReportUtil.exportToPdf(templateFileInfoVo.getPath(), param, detail, response);

        // 上传至文件服务器
        return fileInfoVO;
    }
}
