package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.transfer.ModelMapper;
import com.basic.cloud.uums.entity.AnonymousInfo;
import com.basic.cloud.uums.mapper.AnonymousInfoMapper;
import com.basic.cloud.uums.service.AnonymousInfoService;
import com.basic.cloud.uums.vo.AnonymousInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class AnonymousInfoServiceImpl extends BaseBeanServiceImpl<AnonymousInfoMapper, AnonymousInfo> implements AnonymousInfoService {
    @Override
    public List<AnonymousInfoVO> getAllAnonymousInfo() {
        List<AnonymousInfo> anonymousInfos = list(getLambdaQueryWrapper().eq(BisDataEntity::getDelFlag, false));
        return ModelMapper.mapFromCollection(AnonymousInfoVO.class, anonymousInfos);
    }
}
