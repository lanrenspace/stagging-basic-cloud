package com.ywkj.cloud.basic.print.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.api.FileInfoFeignClient;
import com.basic.cloud.file.dto.ByteReqDTO;
import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.dto.PrintDataDto;
import com.ywkj.cloud.basic.print.dto.PrintDetailDto;
import com.ywkj.cloud.basic.print.dto.PrintFieldDto;
import com.ywkj.cloud.basic.print.entity.PrintRecord;
import com.ywkj.cloud.basic.print.entity.PrintTemplateInfo;
import com.ywkj.cloud.basic.print.mapper.PrintMapper;
import com.ywkj.cloud.basic.print.service.PrintService;
import com.ywkj.cloud.basic.print.service.PrintTemplateInfoService;
import com.ywkj.cloud.basic.print.utils.JasperReportUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
    public FileInfoVO printPdf(PrintDataDto printDataDto) throws Exception {
        FileInfoVO fileInfoVO = new FileInfoVO();

        // 获取模板ID
        LambdaQueryWrapper<PrintTemplateInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PrintTemplateInfo::getNo, printDataDto.getTemplateNo());

        // 找到对应的打印方案
        List<PrintTemplateInfo> printTemplateInfos = printTemplateInfoService.list(lqw);
        Assert.isTrue(!CollectionUtils.isEmpty(printTemplateInfos), "找不到对应的打印方案："+printDataDto.getTemplateNo());

        // 查找文件
        ResultData<FileInfoVO> templateFileInfoVo = fileInfoFeignClient.getFileInfoById(printTemplateInfos.get(0).getFileId());
        Assert.isTrue(!ObjectUtils.isEmpty(templateFileInfoVo), "查找文件为空："+printTemplateInfos.get(0).getFileId());

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

        byte[] byt = JasperReportUtil.exportToPdf(templateFileInfoVo.getData().getPath(), param, detail);

        ByteReqDTO byteReqDTO = new ByteReqDTO();
        byteReqDTO.setBytes(byt);
        byteReqDTO.setFileName(getIdStrategy().id()+".PDF");
        ResultData<FileInfoVO> result = fileInfoFeignClient.byteUpload(byteReqDTO);
        // 上传至文件服务器
        return result.getData();
    }
}
