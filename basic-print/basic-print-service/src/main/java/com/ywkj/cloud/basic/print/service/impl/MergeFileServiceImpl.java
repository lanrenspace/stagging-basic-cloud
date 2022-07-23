package com.ywkj.cloud.basic.print.service.impl;

import cn.hutool.core.io.FileUtil;
import com.basic.cloud.common.base.IdInjectionStrategy;
import com.basic.cloud.common.utils.AppContextHelper;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.file.api.FileInfoFeignClient;
import com.basic.cloud.file.dto.ByteReqDTO;
import com.basic.cloud.file.vo.FileInfoVO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.ywkj.cloud.basic.print.dto.merge.MergeFileReqDto;
import com.ywkj.cloud.basic.print.enums.ReportTypeEnum;
import com.ywkj.cloud.basic.print.service.MergeFileService;
import com.ywkj.cloud.basic.print.utils.RotateImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MergeFileServiceImpl implements MergeFileService {

    @Resource
    FileInfoFeignClient fileInfoFeignClient;

    @Override
    public FileInfoVO mergeFile(MergeFileReqDto mergeFileReqDto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 图片的集合
        List<FileInfoVO> imageCollect = mergeFileReqDto.getFiles().stream().filter(e -> {
            return ReportTypeEnum.isImage(getFileExt(e.getPath()));
        }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(imageCollect)) {
            // new一个pdf文档   PageSize.A4：是设置文档大小的意思 一般都是A4标准
            Document document = new Document(PageSize.A4.rotate());

            try {
                PdfWriter.getInstance(document, baos);
                document.open();

                for (FileInfoVO fileInfoVO : imageCollect) {
                    Image image = Image.getInstance(fileInfoVO.getPath());
                    float height = image.getHeight();
                    float width = image.getWidth();
                    // 图片需要旋转下
                    if (height > width) {
                        image.setRotationDegrees(90);
                    }
                    int percent = getPercent2(height, width);
                    //设置图片居中显示
                    image.setAlignment(Image.MIDDLE);
                    image.scalePercent(percent);//表示是原来图像的比例;
                    document.add(image);
                    document.newPage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (!ObjectUtils.isEmpty(document)) {
                    document.close();
                }
            }
        }

        // pdf的集合
        List<FileInfoVO> pdfCollect = mergeFileReqDto.getFiles().stream().filter(e -> {
            return ReportTypeEnum.isPDF(getFileExt(e.getPath()));
        }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(pdfCollect)) {
            List<PdfReader> collect = pdfCollect.stream().map(e -> {
                try {
                    return new PdfReader(e.getPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());

            try {
                if (baos.size() > 0) {
                    collect.add(new PdfReader(baos.toByteArray()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            baos = mergerPdf(collect, new ByteArrayOutputStream());
        }

        byte[] byt = baos.toByteArray();
        Assert.isTrue(!ObjectUtils.isEmpty(byt), "pdf打印文件生成失败");

        ByteReqDTO byteReqDTO = new ByteReqDTO();
        byteReqDTO.setBytes(byt);
        byteReqDTO.setFileName(AppContextHelper.getBean(IdInjectionStrategy.class).id()+".PDF");
        ResultData<FileInfoVO> result = fileInfoFeignClient.byteUpload(byteReqDTO);

        return result.getData();
    }


    public static ByteArrayOutputStream mergerPdf(List<PdfReader> readers, ByteArrayOutputStream outputStream){
        Document document = new Document();
        try{
            PdfCopy copy = new PdfCopy(document, outputStream);
            document.open();
            int n;
            for(int i = 0 ; i < readers.size(); i++){
                PdfReader reader = readers.get(i);
                n = reader.getNumberOfPages();
                for(int page = 0; page < n;){
                    copy.addPage(copy.getImportedPage(reader, ++page));
                }
                copy.freeReader(reader);
                reader.close();
            }
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outputStream;
    }


    private ReportTypeEnum getFileExt(String fileName) {
        return ReportTypeEnum.get(fileName.substring(fileName.lastIndexOf(".") + 1));
    }

    /**
     * 第二种解决方案，统一按照宽度压缩
     * 这样来的效果是，所有图片的宽度是相等的，自我认为给客户的效果是最好的
     * @param h
     * @param w
     */
    public int getPercent2(float h,float w)
    {
        int p=0;
        float p2=0.0f;
        p2=530/w*100;
        p=Math.round(p2);
        return p;
    }
}
