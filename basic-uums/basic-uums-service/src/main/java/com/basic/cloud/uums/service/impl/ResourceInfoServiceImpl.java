package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.contstant.OrgConst;
import com.basic.cloud.common.contstant.SysConst;
import com.basic.cloud.common.transfer.ModelMapper;
import com.basic.cloud.common.utils.RedisUtil;
import com.basic.cloud.uums.contstant.UumCacheDefine;
import com.basic.cloud.uums.entity.ResourceInfo;
import com.basic.cloud.uums.mapper.ResourceInfoMapper;
import com.basic.cloud.uums.service.ResourceInfoService;
import com.basic.cloud.uums.vo.ResourceUrlVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@AllArgsConstructor
@Service
public class ResourceInfoServiceImpl extends BaseBeanServiceImpl<ResourceInfoMapper, ResourceInfo> implements ResourceInfoService {

    /**
     * 资源KEY链接符号
     */
    private static final String RESOURCE_KEY_LINK_SYMBOL = ":";

    private final RedisUtil redisUtil;

    @Override
    public List<String> getAllResourceUrl(String appId) {
        return list(getLambdaQueryWrapper()
                .eq(!ObjectUtils.isEmpty(appId), ResourceInfo::getAppId, appId)
                .eq(BisDataEntity::getDelFlag, false)).stream()
                .map(resourceInfo -> resourceInfo.getUrl() + RESOURCE_KEY_LINK_SYMBOL + resourceInfo.getMethod()).collect(Collectors.toList());
    }

    @Override
    public boolean matchRequestUrl(String url, String httpMethod) {
        if (ObjectUtils.isEmpty(url) || ObjectUtils.isEmpty(httpMethod)) {
            return false;
        }
        List<String> allResourceUrl = getAllResourceUrl(null);
        if (CollectionUtils.isEmpty(allResourceUrl)) {
            return false;
        }
        String resourceKey = url + RESOURCE_KEY_LINK_SYMBOL + httpMethod;
        return allResourceUrl.contains(resourceKey);
    }

    @Override
    public boolean matchUserResources(List<Long> resourceIds, String url, String httpMethod) {
        boolean existInResources = this.matchRequestUrl(url, httpMethod);
        if (!existInResources) {
            logger.warn("url未在资源中找到，拒绝访问: url:{},httpMethod:{}", url, httpMethod);
            return false;
        }
        return listByIds(resourceIds).stream().anyMatch(resourceInfo ->
                (resourceInfo.getUrl() + RESOURCE_KEY_LINK_SYMBOL + resourceInfo.getMethod()).equals(url + RESOURCE_KEY_LINK_SYMBOL + httpMethod));
    }

    @Override
    public void saveResourceUrl(List<ResourceUrlVO> resourceUrlVOS) {
        if (CollectionUtils.isEmpty(resourceUrlVOS)) {
            return;
        }
        resourceUrlVOS = resourceUrlVOS.stream()
                .filter(resourceUrlVO -> !ObjectUtils.isEmpty(resourceUrlVO.getAppId())
                        && !ObjectUtils.isEmpty(resourceUrlVO.getUrl())
                        && !ObjectUtils.isEmpty(resourceUrlVO.getMethod()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(resourceUrlVOS)) {
            return;
        }
        List<ResourceInfo> resourceInfos = ModelMapper.mapFromCollection(ResourceInfo.class, resourceUrlVOS, (source, target) -> {
            target.setDelFlag(false);
            target.setTenantCode(OrgConst.ADMIN_TENANT_CODE);
            target.setCreateBy(SysConst.ANONYMOUS_USER);
            target.setCreateTime(new Date());
        });
        List<ResourceInfo> existResource = list(getLambdaQueryWrapper()
                .in(ResourceInfo::getAppId, resourceInfos.stream().map(ResourceInfo::getAppId).collect(Collectors.toList()))
                .in(ResourceInfo::getUrl, resourceInfos.stream().map(ResourceInfo::getUrl).collect(Collectors.toList()))
                .in(ResourceInfo::getMethod, resourceInfos.stream().map(ResourceInfo::getMethod).collect(Collectors.toList()))
                .eq(BisDataEntity::getDelFlag, false));
        if (!CollectionUtils.isEmpty(existResource)) {
            resourceInfos = resourceInfos.stream()
                    .filter(resourceInfo -> existResource.stream()
                            .noneMatch(exist -> resourceInfo.getUrl().equals(exist.getUrl()) && resourceInfo.getMethod().equals(exist.getMethod())))
                    .collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(resourceInfos)) {
            batchInsert(resourceInfos);
        }
    }

    @Override
    public void refreshCache(String appId) {
        List<String> allResourceUrl = this.getAllResourceUrl(appId);
        if (null == allResourceUrl) {
            allResourceUrl = new ArrayList<>();
        }
        this.redisUtil.set(UumCacheDefine.RES_URL_KEY, allResourceUrl);
    }

}
