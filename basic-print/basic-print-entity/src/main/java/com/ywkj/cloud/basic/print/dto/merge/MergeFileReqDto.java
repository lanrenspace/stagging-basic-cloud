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

    /**
     * 横向
     */
    public static final int HORIZONTAL = 0;



    @ApiModelProperty(value = "文件列表")
    List<FileInfoVO> files;

    @ApiModelProperty(value = "合并后文件格式")
    ReportTypeEnum toExt;

    @ApiModelProperty(value = "方向(1：是纵向，2：横向)")
    Integer direction;
}
