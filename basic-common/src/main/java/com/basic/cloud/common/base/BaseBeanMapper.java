package com.basic.cloud.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basic.cloud.common.bean.BisDataEntity;

import java.util.Collection;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface BaseBeanMapper<T extends BisDataEntity<T>> extends BaseMapper<T> {

    /**
     * 批量插入
     *
     * @param entityList
     * @return
     */
    int batchInsert(Collection<T> entityList);


    /**
     * 批量更新
     *
     * @param entityList
     * @return
     */
    int batchUpdate(Collection<T> entityList);
}
