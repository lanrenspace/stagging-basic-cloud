package com.basic.cloud.uaa.boot;

import com.basic.cloud.common.boot.PlatformProperties;
import com.basic.cloud.uaa.exception.OAuth2ExceptionTranslator;
import com.basic.cloud.uaa.filter.UaaClientCredentialsTokenEndpointFilter;
import com.basic.cloud.uums.api.UnitFeignClient;
import com.basic.cloud.uums.api.UserGroupRoleFeignClient;
import com.basic.cloud.uums.api.UserInfoFeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@EnableAuthorizationServer
public class UaaOauthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final KeyPair keyPair;
    private final UserInfoFeignClient userInfoFeignClient;
    private final UserGroupRoleFeignClient userGroupRoleService;
    private final UnitFeignClient unitFeignClient;
    private final DataSource dataSource;

    private final PlatformProperties properties;

    public UaaOauthServerConfig(AuthenticationManager authenticationManager, KeyPair keyPair,
                                UserInfoFeignClient userInfoFeignClient, UserGroupRoleFeignClient userGroupRoleService,
                                UnitFeignClient unitFeignClient, DataSource dataSource, PlatformProperties properties) {
        this.authenticationManager = authenticationManager;
        this.keyPair = keyPair;
        this.userInfoFeignClient = userInfoFeignClient;
        this.userGroupRoleService = userGroupRoleService;
        this.unitFeignClient = unitFeignClient;
        this.dataSource = dataSource;
        this.properties = properties;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
        UaaClientCredentialsTokenEndpointFilter endpointFilter = new UaaClientCredentialsTokenEndpointFilter(security);
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
//        security.addTokenEndpointAuthenticationFilter(endpointFilter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints.tokenStore(tokenStore())
                .tokenServices(authorizationServerTokenServices())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .exceptionTranslator(new OAuth2ExceptionTranslator())
                .setClientDetailsService(new JdbcClientDetailsService(dataSource));
    }

    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(this.keyPair);
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter(userInfoFeignClient, userGroupRoleService, unitFeignClient));
        jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConverter);
        return jwtAccessTokenConverter;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new UaaAuthenticationEntryPoint();
    }

    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices defaultTokenServices = new
                DefaultTokenServices();
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        defaultTokenServices.setAccessTokenValiditySeconds(properties.getJwt().getAccessTokenValiditySeconds());
        defaultTokenServices.setRefreshTokenValiditySeconds(properties.getJwt().getRefreshTokenValiditySeconds() + (86400 * 3));
        return defaultTokenServices;
    }

}
