package com.basic.cloud.uaa.boot;

import com.basic.cloud.uaa.exception.OAuth2ExceptionTranslator;
import com.basic.cloud.uums.UnitFeignClient;
import com.basic.cloud.uums.UserGroupRoleFeignClient;
import com.basic.cloud.uums.UserInfoFeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@EnableAuthorizationServer
public class UaaAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private DataSource dataSource;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private KeyPair keyPair;
    private UserInfoFeignClient userInfoFeignClient;
    private UserGroupRoleFeignClient userGroupRoleService;
    private final UnitFeignClient unitFeignClient;

    public UaaAuthorizationServerConfig(DataSource dataSource, AuthenticationConfiguration authenticationConfiguration,
                                        UserDetailsService userDetailsService, KeyPair keyPair,
                                        UserInfoFeignClient userInfoFeignClient, UserGroupRoleFeignClient userGroupRoleService,
                                        UnitFeignClient unitFeignClient) throws Exception {
        this.dataSource = dataSource;
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.userDetailsService = userDetailsService;
        this.keyPair = keyPair;
        this.userInfoFeignClient = userInfoFeignClient;
        this.userGroupRoleService = userGroupRoleService;
        this.unitFeignClient = unitFeignClient;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager).userDetailsService(userDetailsService)
                .accessTokenConverter(jwtAccessTokenConverter());
        endpoints.tokenStore(jwtTokenStore()).setClientDetailsService(new JdbcClientDetailsService(dataSource));
        endpoints.exceptionTranslator(webResponseExceptionTranslator());
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JdbcTokenStore(dataSource);
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

    /**
     * 异常处理
     *
     * @return
     */
    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new OAuth2ExceptionTranslator();
    }


}
