package com.basic.cloud.uaa.oauth2.userdetail;

import com.basic.cloud.uaa.entity.UserJwt;
import com.basic.cloud.uaa.multiple.MultipleAuthentication;
import com.basic.cloud.uaa.multiple.MultipleAuthenticationContext;
import com.basic.cloud.uaa.multiple.authenticator.MultipleAuthenticator;
import com.basic.cloud.uums.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service("userDetailsService")
public class MultipleUserDetailsService implements UserDetailsService {

    private List<MultipleAuthenticator> authenticators;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MultipleAuthentication multipleAuthentication = MultipleAuthenticationContext.get();
        if (null == multipleAuthentication) {
            multipleAuthentication = new MultipleAuthentication();
        }
        multipleAuthentication.setUsername(username);
        UserInfo userInfo = this.authenticate(multipleAuthentication);
        if (null == userInfo) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new UserJwt(userInfo.getAccount(), userInfo.getPassword(), !userInfo.getDelFlag(), true, true, true, this.grantedAuthorities(userInfo), userInfo.getId());
    }

    @Autowired(required = false)
    public void setAuthenticators(List<MultipleAuthenticator> authenticators) {
        this.authenticators = authenticators;
    }

    /**
     * 获得登录者所有角色的权限集合.
     *
     * @param userInfo
     * @return
     */
    protected Set<GrantedAuthority> grantedAuthorities(UserInfo userInfo) {
        try {
            HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("NONE"));
            return grantedAuthorities;
        } catch (Exception e) {
            e.printStackTrace();
            HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("NONE"));
            return grantedAuthorities;
        }
    }


    /**
     * 执行认证
     *
     * @param multipleAuthentication
     * @return
     */
    private UserInfo authenticate(MultipleAuthentication multipleAuthentication) {
        if (this.authenticators != null) {
            for (MultipleAuthenticator authenticator : this.authenticators) {
                if (authenticator.support(multipleAuthentication)) {
                    return authenticator.authenticate(multipleAuthentication);
                }
            }
        }
        return null;
    }
}
