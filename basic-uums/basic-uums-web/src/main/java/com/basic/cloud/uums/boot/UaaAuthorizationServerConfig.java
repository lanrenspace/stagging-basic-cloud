package com.basic.cloud.uums.boot;

import com.basic.cloud.common.utils.RedisUtil;
import com.basic.cloud.uaa.exception.OAuth2ExceptionTranslator;
import com.basic.cloud.uaa.multiple.MultipleAuthenticationFilter;
import com.basic.cloud.uaa.oauth2.enhancer.UaaTokenEnhancer;
import com.basic.cloud.uums.api.UserInfoFeignClient;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@EnableAuthorizationServer
public class UaaAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    final DataSource dataSource;
    final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final MultipleAuthenticationFilter multipleAuthenticationFilter;
    private final RedisUtil redisUtil;
    private final UserInfoFeignClient userInfoFeignClient;

    /**
     * jwt 对称加密密钥
     */
    @Value("${uaa.auth.jwt.signingKey}")
    private String signingKey;

    public UaaAuthorizationServerConfig(DataSource dataSource, @Qualifier("userDetailsService") UserDetailsService userDetailsService,
                                        AuthenticationManager authenticationManager, MultipleAuthenticationFilter multipleAuthenticationFilter,
                                        RedisUtil redisUtil, UserInfoFeignClient userInfoFeignClient) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.multipleAuthenticationFilter = multipleAuthenticationFilter;
        this.redisUtil = redisUtil;
        this.userInfoFeignClient = userInfoFeignClient;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .addTokenEndpointAuthenticationFilter(multipleAuthenticationFilter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .reuseRefreshTokens(false)
                .authorizationCodeServices(authorizationCodeServices())
                .approvalStore(approvalStore())
                .exceptionTranslator(customExceptionTranslator())
                .tokenEnhancer(tokenEnhancerChain())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenGranter(tokenGranter(endpoints));
    }

    /**
     * 自定义OAuth2异常处理
     */
    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> customExceptionTranslator() {
        return new OAuth2ExceptionTranslator();
    }

    /**
     * 授权信息持久化实现
     *
     * @return JdbcApprovalStore
     */
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }


    /**
     * 授权码模式持久化授权码code
     */
    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * token的持久化
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new UaaTokenEnhancer(redisUtil, userInfoFeignClient), accessTokenConverter()));
        return tokenEnhancerChain;
    }

    /**
     * jwt token的生成配置
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    /**
     * 配置自定义的granter
     */
    public TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = Lists.newArrayList(endpoints.getTokenGranter());
        return new CompositeTokenGranter(granters);
    }
}
