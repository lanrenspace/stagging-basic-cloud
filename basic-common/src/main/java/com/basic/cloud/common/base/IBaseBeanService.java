package com.basic.cloud.common.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface IBaseBeanService<T> extends IService<T> {

    /**
     * 获取bean对应的class
     *
     * @return
     */
    Class<T> getBeanClass();


    /**
     * lambda 查询器
     *
     * @return
     */
    LambdaQueryWrapper<T> getLambdaQueryWrapper();


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

    /**
     * 获取ID生成策略
     *
     * @return
     */
    default IdInjectionStrategy getIdStrategy() {
        return null;
    }
}
