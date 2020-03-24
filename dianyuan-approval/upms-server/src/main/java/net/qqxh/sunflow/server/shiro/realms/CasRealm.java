package net.qqxh.sunflow.server.shiro.realms;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.shiro.service.AbstractShiroService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈shiro单点登录权限信息提供类〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: CasRealm.java
 * @date: 2019/5/29 20:32
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CasRealm extends Pac4jRealm {
    @Autowired
    AbstractShiroService shiroService;
    private String clientName;

    private final static Logger LOG = LoggerFactory.getLogger(CasRealm.class);
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Pac4jToken;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;
        final List<CommonProfile> commonProfileList = pac4jToken.getProfiles();
        final CommonProfile commonProfile = commonProfileList.get(0);
        LOG.info("==> casAuthenticationInfo{}"+commonProfile.toString());
        final Pac4jPrincipal principal = new Pac4jPrincipal(commonProfileList, getPrincipalNameAttribute());
        ShiroUser shiroUser = shiroService.getUserByLoginName(principal.getName());
        final PrincipalCollection principalCollection = new SimplePrincipalCollection(shiroUser, getName());
        if (shiroUser == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(principalCollection, commonProfileList.hashCode());
    }

    /**
     * 授权/验权
     *
     * @param
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(shiroUser.getRoles());
        return info;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}