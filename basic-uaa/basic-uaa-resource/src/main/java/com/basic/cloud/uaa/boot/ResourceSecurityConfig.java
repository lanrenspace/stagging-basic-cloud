package com.basic.cloud.uaa.boot;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@EnableWebSecurity
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/api/**", "/*.html", "/static/api/*.html", "/actuator/**", "/login","/uum/userExt/uploadLoginFails").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        http.httpBasic().disable();
    }
}
