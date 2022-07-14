package com.basic.cloud.uaa.service;

import com.basic.cloud.common.bean.SecurityUser;
import com.basic.cloud.uums.api.UserInfoFeignClient;
import com.basic.cloud.uums.entity.UserInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class UaaUserDetailsService implements UserDetailsService {

    private final UserInfoFeignClient userInfoFeignClient;

    public UaaUserDetailsService(UserInfoFeignClient userInfoFeignClient) {
        this.userInfoFeignClient = userInfoFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoFeignClient.queryUserByAccount(s);
        if (null == userInfo) {
            throw new UsernameNotFoundException(s + " 用户不存在");
        }
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        List<String> roles = Arrays.asList(new String[]{});
        SecurityUser user = new SecurityUser(userInfo.getAccount(), userInfo.getPassword(), true,
                true, true, true);
        user.setRoles(roles);
        return (UserDetails) user;

    }
}
