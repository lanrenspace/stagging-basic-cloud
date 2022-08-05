package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.transfer.ModelMapper;
import com.basic.cloud.common.utils.RedisUtil;
import com.basic.cloud.uums.contstant.UumCacheDefine;
import com.basic.cloud.uums.entity.AnonymousInfo;
import com.basic.cloud.uums.mapper.AnonymousInfoMapper;
import com.basic.cloud.uums.service.AnonymousInfoService;
import com.basic.cloud.uums.vo.AnonymousInfoVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@AllArgsConstructor
@Service
public class AnonymousInfoServiceImpl extends BaseBeanServiceImpl<AnonymousInfoMapper, AnonymousInfo> implements AnonymousInfoService {

    private final RedisUtil redisUtil;

    @Override
    public List<AnonymousInfoVO> getAllAnonymousInfo(String appId) {
        List<AnonymousInfo> anonymousInfos = list(getLambdaQueryWrapper()
                .eq(!ObjectUtils.isEmpty(appId), AnonymousInfo::getAppId, appId).eq(BisDataEntity::getDelFlag, false));
        return ModelMapper.mapFromCollection(AnonymousInfoVO.class, anonymousInfos);
    }

    @Override
    public void refreshCache(String appId) {
        List<AnonymousInfoVO> allAnonymousInfo = this.getAllAnonymousInfo(appId);
        if (null == allAnonymousInfo) {
            allAnonymousInfo = new ArrayList<>();
        }
        this.redisUtil.set(UumCacheDefine.ANONYMOUS_INFO_KEY, allAnonymousInfo, -1L);
    }
}
