package com.basic.cloud.common.bean;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.cloud.common.base.BaseBeanMapper;
import com.basic.cloud.common.base.IBaseBeanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class BaseBeanServiceImpl<T extends BisDataEntity<T>, M extends BaseBeanMapper<T>> extends ServiceImpl<M, T> implements IBaseBeanService<T>, InitializingBean {

    /**
     * log
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 业务对象Class
     */
    private Class<T> beanClass;

    @Override
    public Class<T> getBeanClass() {
        return beanClass;
    }

    @Override
    public LambdaQueryWrapper<T> getLambdaQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }

    @Override
    public int batchInsert(Collection<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new NullPointerException("entityList不能为空!");
        }
        return getBaseMapper().batchInsert(entityList);
    }

    @Override
    public int batchUpdate(Collection<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new NullPointerException("entityList不能为空!");
        }
        return getBaseMapper().batchUpdate(entityList);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            this.beanClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        }
    }
}
