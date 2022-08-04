package com.basic.cloud.gateway.service.impl;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.common.vo.business.UrlResVO;
import com.basic.cloud.gateway.service.PlatformHandler;
import com.basic.cloud.gateway.service.PlatformService;
import com.basic.cloud.uums.vo.ResourceUrlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class PlatformHandlerImpl implements PlatformHandler {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(PlatformHandlerImpl.class);

    private final GatewayProperties gatewayProperties;
    private final RestTemplate restTemplate;
    private final PlatformService platformService;

    public PlatformHandlerImpl(GatewayProperties gatewayProperties, RestTemplate restTemplate,
                               PlatformService platformService) {
        this.gatewayProperties = gatewayProperties;
        this.restTemplate = restTemplate;
        this.platformService = platformService;
    }

    @Override
    public Mono<ServerResponse> initRouteResourceUrl(ServerRequest request) {
        ServerResponse.BodyBuilder bodyBuilder = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8);
        if (!this.platformService.isAllowedResUrlInit()) {
            return bodyBuilder.body(BodyInserters.fromObject(ResultData.error("当前环境不允许进行此操作!")));
        }
        String serviceName = request.queryParam("serviceName").orElse("");
        Map<String, URI> serviceUriMap = new HashMap<>();
        if (StringUtils.hasLength(serviceName)) {
            Optional<RouteDefinition> definitionOptional = gatewayProperties.getRoutes().stream()
                    .filter(routeDefinition -> routeDefinition.getId().equals(serviceName)).findFirst();
            if (definitionOptional.isPresent()) {
                RouteDefinition routeDefinition = definitionOptional.get();
                serviceUriMap.put(routeDefinition.getId(), routeDefinition.getUri());
            }
        } else {
            for (RouteDefinition route : gatewayProperties.getRoutes()) {
                serviceUriMap.put(route.getId(), route.getUri());
            }
        }
        if (serviceUriMap.keySet().size() > 0) {
            try {
                List<ResourceUrlVO> resourceUrlVOS = new ArrayList<>();
                for (Map.Entry<String, URI> entry : serviceUriMap.entrySet()) {
                    String service = entry.getKey();
                    String httpProtocolUri = entry.getValue().toString().replace("lb:", "http:");
                    ResultData<List<UrlResVO>> resultData = restTemplate.getForObject(new URI(httpProtocolUri + "/platform/init-load-resource-url"), ResultData.class);
                    if (resultData != null && resultData.getSuccess()) {
                        List<ResourceUrlVO> urlVOS = resultData.getData().stream()
                                .map(item -> new ResourceUrlVO(service, item.getUrl(), item.getUrl(), item.getMethod()))
                                .collect(Collectors.toList());
                        resourceUrlVOS.addAll(urlVOS);
                    } else {
                        logger.error("init-route-resource-url fail. serviceName={}", entry.getValue().toString());
                    }
                }
                platformService.resUrlInitRemote(resourceUrlVOS);
            } catch (Exception e) {
                logger.error("init-route-resource-url error. msg={}", e.getMessage());
                return bodyBuilder.body(BodyInserters.fromObject(ResultData.error()));
            }
        }
        return bodyBuilder.body(BodyInserters.fromObject(ResultData.ok()));
    }
}
