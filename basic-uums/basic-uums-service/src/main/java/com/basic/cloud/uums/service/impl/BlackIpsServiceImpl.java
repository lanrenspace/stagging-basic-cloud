package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.exceptions.DataException;
import com.basic.cloud.common.transfer.ModelMapper;
import com.basic.cloud.uums.entity.BlackIps;
import com.basic.cloud.uums.mapper.BlackIpsMapper;
import com.basic.cloud.uums.service.BlackIpsService;
import com.basic.cloud.uums.vo.BlackIpVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class BlackIpsServiceImpl extends BaseBeanServiceImpl<BlackIpsMapper, BlackIps> implements BlackIpsService {
    @Override
    public List<BlackIpVO> getAllBlackIp() {
        List<BlackIps> blackIps = list(getLambdaQueryWrapper().eq(BisDataEntity::getDelFlag, false)
                .isNull(BlackIps::getDeadlineDate).or().gt(BlackIps::getDeadlineDate, new Date()));
        if (CollectionUtils.isEmpty(blackIps)) {
            return new ArrayList<>();
        }
        return ModelMapper.mapFromCollection(BlackIpVO.class, blackIps);
    }

    @Override
    public Boolean saveIp(BlackIps blackIps) {
        BlackIps dbBlackIp = getOne(getLambdaQueryWrapper().eq(BlackIps::getIp, blackIps.getIp())
                .eq(BisDataEntity::getDelFlag, false));
        if (dbBlackIp != null) {
            throw new DataException("数据已存在,请删除后重新添加!");
        }
        return save(blackIps);
    }
}
