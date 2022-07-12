package com.basic.cloud.uaa.boot;

import com.basic.cloud.uaa.service.UaaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
public class UaaSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UaaUserDetailsService uaaUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UaaSecurityConfig(UaaUserDetailsService uaaUserDetailsService, PasswordEncoder passwordEncoder) {
        this.uaaUserDetailsService = uaaUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(uaaUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 让userNotFoundException 异常能正常抛出来
     *
     * @param userDetailsService
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }
}
