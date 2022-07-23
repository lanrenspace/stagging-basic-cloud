package com.ywkj.cloud.basic.print.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportTypeEnum {
    HTML("HTML"),
    PDF("PDF"),
    XLS("XLS"),
    XLSX("XLSX"),
    XML("XML"),
    RTF("RTF"),
    CSV("CSV"),
    DOC("DOC"),
    JPG("JPG"),
    JPEG("JPEG"),
    PNG("PNG"),
    ;

    private String fileExt;

    public static ReportTypeEnum get(String fileExt) {
        for (ReportTypeEnum ext : values()) {
            if (ext.getFileExt().toUpperCase().equals(fileExt.toUpperCase())) {
                //获取指定的枚举
                return ext;
            }
        }
        return null;
    }

    public static boolean isImage(ReportTypeEnum typeEnum) {
        switch (typeEnum) {
            case JPG:
            case JPEG:
            case PNG: {
                return true;
            }
        }
        return false;
    }

    public static boolean isPDF(ReportTypeEnum typeEnum) {
        switch (typeEnum) {
            case PDF: {
                return true;
            }
        }
        return false;
    }

}
