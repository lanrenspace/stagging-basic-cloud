package com.basic.cloud.common.utils;

import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.common.boot.UserSetting;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author lanrenspace@163.com
 * @Description: 当前登录用户操作信息
 **/
public final class UserUtil {

    /**
     * 获取授权用户
     *
     * @return
     */
    public static UserDetail getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return getUser(request);
    }

    /**
     * 获取授权用户
     *
     * @param request
     * @return
     */
    public static UserDetail getUser(HttpServletRequest request) {
        return AppContextHelper.getBean(UserSetting.class).getUser(request);
    }
}
