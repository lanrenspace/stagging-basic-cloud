package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.uums.entity.ResourceInfo;
import com.basic.cloud.uums.vo.ResourceUrlVO;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface ResourceInfoService extends IBaseBeanService<ResourceInfo> {

    /**
     * 获取所有的资源url
     *
     * @return url：格式 = url:method
     */
    List<String> getAllResourceUrl();

    /**
     * 根据请求url匹配资源url
     *
     * @param url
     * @param httpMethod
     * @return
     */
    boolean matchRequestUrl(String url, String httpMethod);

    /**
     * 判断请求url是否匹配当前用户所拥有的资源
     *
     * @param resourceIds
     * @param url
     * @return
     */
    boolean matchUserResources(List<Long> resourceIds, String url, String httpMethod);

    /**
     * 保存资源信息
     *
     * @param resourceUrlVOS
     */
    void saveResourceUrl(List<ResourceUrlVO> resourceUrlVOS);

}
