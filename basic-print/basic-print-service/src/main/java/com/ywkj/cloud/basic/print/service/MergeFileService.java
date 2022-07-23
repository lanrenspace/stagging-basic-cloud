package com.ywkj.cloud.basic.print.service;

import com.basic.cloud.file.vo.FileInfoVO;
import com.lowagie.text.DocumentException;
import com.ywkj.cloud.basic.print.dto.merge.MergeFileReqDto;

import java.io.IOException;

public interface MergeFileService {

    /**
     * 合并文件
     * @param mergeFileReqDto
     * @return
     */
    FileInfoVO mergeFile(MergeFileReqDto mergeFileReqDto) throws DocumentException, IOException;
}
