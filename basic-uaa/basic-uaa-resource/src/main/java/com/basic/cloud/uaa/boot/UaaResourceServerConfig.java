package com.basic.cloud.uaa.boot;

import com.basic.cloud.uaa.exception.OAuth2AuthenticationEntryPoint;
import com.basic.cloud.uaa.exception.OAuth2ExceptionHandler;
import com.basic.cloud.uaa.exception.OAuth2ExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@EnableResourceServer
@EnableWebSecurity
public class UaaResourceServerConfig extends ResourceServerConfigurerAdapter {

    private KeyPair keyPair;

    private final Environment environment;

    public UaaResourceServerConfig(KeyPair keyPair, Environment environment) {
        this.keyPair = keyPair;
        this.environment = environment;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        String resourceName = environment.getProperty("spring.application.name", "-1");
        Assert.isTrue(!"-1".equals(resourceName), "请配置spring.application.name参数");
        resources.resourceId(resourceName).tokenStore(tokenStore());
        resources.accessDeniedHandler(new OAuth2ExceptionTranslator());
        resources.authenticationEntryPoint(new OAuth2AuthenticationEntryPoint());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/uum/**").authenticated()
                .anyRequest().permitAll();
        http.httpBasic().disable();
    }

    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(this.keyPair);
        jwtAccessTokenConverter.setVerifier(new RsaVerifier((RSAPublicKey) this.keyPair.getPublic()));
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
        jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConverter);
        return jwtAccessTokenConverter;
    }
}
