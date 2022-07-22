package com.basic.cloud.common.contstant;

/**
 * @Author lanrenspace@163.com
 * @Description: 文件类型
 **/
public interface FileType {

    /**
     * PNG
     */
    String IMG_PNG = "PNG";

    /**
     * JPG
     */
    String IMG_JPG = "JPG";

    /**
     * JPEG
     */
    String IMG_JPEG = "JPEG";

    /**
     * JASPER
     */
    String JASPER_JPEG = "JASPER";

    /**
     * 默认允许的文件后缀
     *
     * @return
     */
    static String suffixList() {
        return IMG_PNG + "," + IMG_JPG + "," + IMG_JPEG + "," + JASPER_JPEG;
    }
}
