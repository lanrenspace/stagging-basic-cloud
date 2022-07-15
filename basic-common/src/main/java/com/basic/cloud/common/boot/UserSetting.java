package com.basic.cloud.common.boot;

import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.common.contstant.SysConst;
import com.basic.cloud.common.utils.RedisUtil;
import com.basic.cloud.common.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import static com.basic.cloud.common.contstant.SysConst.REQ_USER_ID;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class UserSetting {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(UserUtil.class);

    private final RedisUtil redisUtil;

    public UserSetting(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 获取授权用户信息
     *
     * @param request
     * @return
     */
    public UserDetail getUser(HttpServletRequest request) {
        String reqUserId = request.getHeader(REQ_USER_ID);
        if (ObjectUtils.isEmpty(reqUserId)) {
            logger.warn("非完整请求或匿名用户!");
            UserDetail userDetail = new UserDetail();
            userDetail.setUserId(SysConst.ANONYMOUS_USER);
            return userDetail;
        }
        if (request.getSession(false) != null && request.getSession(false).getAttribute(reqUserId) != null) {
            return (UserDetail) request.getSession(false).getAttribute(REQ_USER_ID);
        }
        UserDetail userDetail = this.redisUtil.get(String.format(SysConst.AUTH_LOGIN_CACHE_KEY, reqUserId), UserDetail.class);
        if (null != userDetail && request.getSession(false) != null) {
            request.getSession(false).setAttribute(reqUserId, userDetail);
        }
        return userDetail;
    }
}
