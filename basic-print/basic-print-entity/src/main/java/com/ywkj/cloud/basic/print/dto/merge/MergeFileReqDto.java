package com.ywkj.cloud.basic.print.dto.merge;

import com.basic.cloud.file.vo.FileInfoVO;
import com.ywkj.cloud.basic.print.enums.ReportTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "文件合并")
public class MergeFileReqDto {

    @ApiModelProperty(value = "文件列表")
    List<FileInfoVO> files;

    @ApiModelProperty(value = "合并后文件格式")
    ReportTypeEnum toExt;
}
