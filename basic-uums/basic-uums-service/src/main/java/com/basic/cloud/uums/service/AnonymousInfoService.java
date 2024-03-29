package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.uums.entity.AnonymousInfo;
import com.basic.cloud.uums.vo.AnonymousInfoVO;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface AnonymousInfoService extends IBaseBeanService<AnonymousInfo> {

    /**
     * 获取所有的白名单信息
     *
     * @param appId
     * @return
     */
    List<AnonymousInfoVO> getAllAnonymousInfo(String appId);

    /**
     * 刷新缓存
     *
     * @param appId
     */
    void refreshCache(String appId);
}
