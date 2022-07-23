package com.ywkj.cloud.basic.print.service.impl;

import cn.hutool.core.io.FileUtil;
import com.basic.cloud.common.base.IdInjectionStrategy;
import com.basic.cloud.common.utils.AppContextHelper;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.api.FileInfoFeignClient;
import com.basic.cloud.file.dto.ByteReqDTO;
import com.basic.cloud.file.vo.FileInfoVO;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.ywkj.cloud.basic.print.dto.merge.MergeFileReqDto;
import com.ywkj.cloud.basic.print.enums.ReportTypeEnum;
import com.ywkj.cloud.basic.print.service.MergeFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@Slf4j
public class MergeFileServiceImpl implements MergeFileService {

    @Resource
    FileInfoFeignClient fileInfoFeignClient;

    @Override
    public FileInfoVO mergeFile(MergeFileReqDto mergeFileReqDto) {
        // new一个pdf文档   PageSize.A4：是设置文档大小的意思 一般都是A4标准
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfCopy copy = null;
        PdfReader reader = null;

        try {
            PdfWriter.getInstance(document, baos);
            //打开文档
            document.open();

            for (int i = 0; i < mergeFileReqDto.getFiles().size(); i++) {
                ReportTypeEnum fileType = getFileExt(mergeFileReqDto.getFiles().get(i).getPath());
                switch (fileType) {
                    case JPG:
                    case JPEG:
                    case PNG:
                        Image image = Image.getInstance(mergeFileReqDto.getFiles().get(i).getPath());
                        document.add(image);
                        document.newPage();
                        break;
                    case PDF: {
                        reader = new PdfReader(mergeFileReqDto.getFiles().get(i).getPath());
                        int n = reader.getNumberOfPages();
                        for (int j = 1; j <= n; j++) {
                            document.newPage();
                            PdfImportedPage page = copy.getImportedPage(reader, j);
                            copy.addPage(page);
                        }
                        reader.close();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (copy != null) {
                copy.close();
            }
            if (document != null) {
                document.close();
            }
        }
        byte[] byt = baos.toByteArray();

        Assert.isTrue(!ObjectUtils.isEmpty(byt), "pdf打印文件生成失败");

        ByteReqDTO byteReqDTO = new ByteReqDTO();
        byteReqDTO.setBytes(byt);
        byteReqDTO.setFileName(AppContextHelper.getBean(IdInjectionStrategy.class).id()+".PDF");
        ResultData<FileInfoVO> result = fileInfoFeignClient.byteUpload(byteReqDTO);

        return result.getData();
    }

    private ReportTypeEnum getFileExt(String fileName) {
        return ReportTypeEnum.get(fileName.substring(fileName.lastIndexOf(".") + 1));
    }
}
