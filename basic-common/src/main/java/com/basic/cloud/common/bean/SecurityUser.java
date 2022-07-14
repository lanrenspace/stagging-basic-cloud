package com.basic.cloud.common.bean;

import com.basic.cloud.common.base.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class SecurityUser implements /*UserDetails,*/ User {
    /**
     * 账号过期
     */
    private boolean accountNonExpired;

    /**
     * 账号锁定
     */
    private boolean accountNonLocked;

    /**
     * 证书过期
     */
    private boolean credentialsNonExpired;

    /**
     * 是否启用
     */
    private boolean enabled;


    /**
     * ip 地址
     */
    private String ip;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;


    /**
     * 用户密码
     */
    private String password;

    /**
     * oauth2 token
     */
    private String token;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户手机号
     */
    private String userMobile;

    /**
     * 机构名称
     */
    private String unitName;

    /**
     * 机构编码
     */
    private String unitCode;

    /**
     * 机构ID
     */
    private String unitId;

    /**
     * 租户管理机构
     */
    private boolean tenantAdminUnit;

    /**
     * 租户管理员
     */
    private boolean tenantAdmin;

    /**
     * 平台管理员
     */
    private boolean admin;

    /**
     * 用户角色
     */
    private List<String> roles;

    public SecurityUser() {

    }

    public SecurityUser(String userAccount, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked) {
        this.userAccount = userAccount;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
    }


    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserAccount() {
        return userAccount;
    }

    @Override
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String getUserType() {
        return userType;
    }

    @Override
    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String getUserMobile() {
        return userMobile;
    }

    @Override
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String getUnitCode() {
        return unitCode;
    }

    @Override
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Override
    public String getUnitId() {
        return unitId;
    }

    @Override
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public boolean isTenantAdminUnit() {
        return tenantAdminUnit;
    }

    @Override
    public void setTenantAdmin(boolean tenantAdmin) {
        this.tenantAdmin = tenantAdmin;
    }

    @Override
    public boolean isTenantAdmin() {
        return tenantAdmin;
    }

    @Override
    public boolean isAdmin() {
        return admin;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> auths = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(this.getRoles())) {
//            this.getRoles().forEach(roleId -> auths.add(new SimpleGrantedAuthority(roleId)));
//        }
//        return auths;
//    }

//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return userAccount;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
