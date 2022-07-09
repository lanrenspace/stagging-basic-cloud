package com.basic.cloud.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description: 分页接口交互
 **/
@Data
public class ResultPage<T> implements Serializable {

    /**
     * 总条数
     */
    private long total;

    /**
     * 数据
     */
    private List<T> rows;

    public ResultPage() {

    }

    public ResultPage(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public ResultPage(int total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
