package com.ywkj.cloud.basic.print.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.dto.PrintDataDto;
import com.ywkj.cloud.basic.print.entity.PrintRecord;

import javax.servlet.http.HttpServletResponse;

public interface PrintService extends IBaseBeanService<PrintRecord> {

    /**
     * 打印pdf文件，并且返回pdf的路径给到前端
     * @param printDataDto
     * @return
     */
    FileInfoVO printPdf(PrintDataDto printDataDto, HttpServletResponse response) throws Exception;
}
