package com.basic.cloud.common.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.cloud.common.base.BaseBeanMapper;
import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.common.base.IdInjectionStrategy;
import com.basic.cloud.common.exceptions.ServiceException;
import com.basic.cloud.common.utils.AppContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class BaseBeanServiceImpl<M extends BaseBeanMapper<T>, T extends BisDataEntity<T>> extends ServiceImpl<M, T> implements IBaseBeanService<T>, InitializingBean {

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

    @Override
    public IdInjectionStrategy getIdStrategy() {
        return AppContextHelper.getBean(IdInjectionStrategy.class);
    }

    @Override
    public boolean removeById(Serializable id) {
        T entity = getById(id);
        entity.setDelFlag(true);
        return updateById(entity);
    }

    @Override
    public boolean save(T entity) {
        boolean insertFlag = false;
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            if (fields.length > 0) {
                Field matchField = Arrays.stream(fields).filter(field -> field.isAnnotationPresent(TableId.class)).findFirst().orElse(null);
                if (null != matchField) {
                    matchField.setAccessible(true);
                    Long pkId = matchField.get(entity) == null ? null : (Long) matchField.get(entity);
                    if (pkId == null) {
                        insertFlag = true;
                        matchField.set(entity, Long.parseLong(getIdStrategy().id()));
                    }
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), entity.getClass().getName() + " 对象获取主键ID异常!");
        }
        return insertFlag ? super.save(entity) : super.updateById(entity);
    }
}
