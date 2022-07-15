package com.basic.cloud.common.boot;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.basic.cloud.common.base.TenantTableFilterConfigManager;
import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.common.contstant.DelEnum;
import com.basic.cloud.common.contstant.SysConst;
import com.basic.cloud.common.utils.UserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

import static com.basic.cloud.common.contstant.BisEntityConst.*;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {


    private final TenantTableFilterConfigManager tenantTableFilterConfigManager;

    public CustomMetaObjectHandler(TenantTableFilterConfigManager tenantTableFilterConfigManager) {
        this.tenantTableFilterConfigManager = tenantTableFilterConfigManager;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        UserDetail userDetail = UserUtil.getUser();
        if (ObjectUtils.isEmpty(getFieldValByName(CREATE_USER_FIELD, metaObject))) {
            setFieldValByName(CREATE_USER_FIELD, null != userDetail ? userDetail.getUserId() : SysConst.ANONYMOUS_USER, metaObject);
        }
        if (ObjectUtils.isEmpty(getFieldValByName(CREATE_USER_NAME, metaObject))) {
            setFieldValByName(CREATE_USER_NAME, null != userDetail ? userDetail.getName() : "", metaObject);
        }
        if (ObjectUtils.isEmpty(getFieldValByName(CREATE_DATE_FIELD, metaObject))) {
            setFieldValByName(CREATE_DATE_FIELD, new Date(), metaObject);
        }
        if (ObjectUtils.isEmpty(getFieldValByName(DEL_FLAG_FIELD, metaObject))) {
            setFieldValByName(DEL_FLAG_FIELD, false, metaObject);
        }
        if (metaObject.hasSetter(TENANT_CODE_FIELD) && metaObject.hasGetter(TENANT_CODE_FIELD)
                && ObjectUtils.isEmpty(getFieldValByName(TENANT_CODE_FIELD, metaObject))) {
            setFieldValByName(TENANT_CODE_FIELD, null != userDetail ? userDetail.getTenantCode() : null, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        UserDetail userDetail = UserUtil.getUser();
        if (metaObject.hasSetter(UPDATE_USER_FIELD) && metaObject.hasGetter(UPDATE_USER_FIELD) && ObjectUtils.isEmpty(getFieldValByName(UPDATE_USER_FIELD, metaObject))) {
            setFieldValByName(UPDATE_USER_FIELD, null != userDetail ? userDetail.getUserId() : SysConst.ANONYMOUS_USER, metaObject);
        }
        if (metaObject.hasSetter(UPDATE_USER_NAME) && metaObject.hasGetter(UPDATE_USER_NAME) && ObjectUtils.isEmpty(getFieldValByName(UPDATE_USER_NAME, metaObject))) {
            setFieldValByName(UPDATE_USER_NAME, null != userDetail ? userDetail.getName() : "", metaObject);
        }
        if (ObjectUtils.isEmpty(getFieldValByName(UPDATE_DATE_FIELD, metaObject))) {
            setFieldValByName(UPDATE_DATE_FIELD, new Date(), metaObject);
        }
    }
}
