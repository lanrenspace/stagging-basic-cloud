package com.basic.cloud.uaa.boot;

import com.basic.cloud.common.boot.PlatformProperties;
import com.basic.cloud.uaa.service.UaaUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@EnableWebSecurity
public class UaaWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UaaUserDetailsService uaaUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final PlatformProperties properties;

    public UaaWebSecurityConfig(UaaUserDetailsService uaaUserDetailsService, PasswordEncoder passwordEncoder, PlatformProperties properties) {
        this.uaaUserDetailsService = uaaUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // "/actuator/**" abs 注册中心端点
                .antMatchers("/actuator/**", "/.well-known/jks.json", "/token/remove").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable().cors().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(uaaUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
