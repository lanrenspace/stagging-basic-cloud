package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.uums.entity.BlackIps;
import com.basic.cloud.uums.vo.BlackIpVO;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface BlackIpsService extends IBaseBeanService<BlackIps> {

    /**
     * 获取所有的黑名单IP信息
     *
     * @return
     */
    List<BlackIpVO> getAllBlackIp();
}
