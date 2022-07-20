package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.uums.entity.ResourceInfo;
import com.basic.cloud.uums.mapper.ResourceInfoMapper;
import com.basic.cloud.uums.service.ResourceInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class ResourceInfoServiceImpl extends BaseBeanServiceImpl<ResourceInfoMapper, ResourceInfo> implements ResourceInfoService {

    /**
     * 资源KEY链接符号
     */
    private static final String RESOURCE_KEY_LINK_SYMBOL = ":";

    @Override
    public List<String> getAllResourceUrl() {
        return list(getLambdaQueryWrapper().eq(BisDataEntity::getDelFlag, false)).stream()
                .map(resourceInfo -> resourceInfo.getUrl() + RESOURCE_KEY_LINK_SYMBOL + resourceInfo.getMethod()).collect(Collectors.toList());
    }

    @Override
    public boolean matchRequestUrl(String url, String httpMethod) {
        if (ObjectUtils.isEmpty(url) || ObjectUtils.isEmpty(httpMethod)) {
            return false;
        }
        List<String> allResourceUrl = getAllResourceUrl();
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
            logger.warn("url未在资源池中找到，拒绝访问: url:{},httpMethod:{}", url, httpMethod);
            return false;
        }
        return listByIds(resourceIds).stream().anyMatch(resourceInfo ->
                (resourceInfo.getUrl() + RESOURCE_KEY_LINK_SYMBOL + resourceInfo.getMethod()).equals(url + RESOURCE_KEY_LINK_SYMBOL + httpMethod));
    }

}
