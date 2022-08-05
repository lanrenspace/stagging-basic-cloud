package com.basic.cloud.gateway.service;

import com.basic.cloud.uums.vo.ResourceUrlVO;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface PlatformService {

    /**
     * 是否允许初始化资源
     *
     * @return
     */
    boolean isAllowedResUrlInit();

    /**
     * 初始化化资源url
     *
     * @param resourceUrlVOS
     */
    void resUrlInitRemote(List<ResourceUrlVO> resourceUrlVOS);
}
