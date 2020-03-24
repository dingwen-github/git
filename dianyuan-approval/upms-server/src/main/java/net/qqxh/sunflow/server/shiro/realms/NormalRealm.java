package net.qqxh.sunflow.server.shiro.realms;


import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.shiro.service.AbstractShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈shiro常规登录权限信息提供类〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: NormalRealm.java
 * @date: 2019/5/29 20:32
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class NormalRealm extends AuthorizingRealm {

    private AbstractShiroService shiroService;
    private final static Logger LOG = LoggerFactory.getLogger(NormalRealm.class);

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(shiroUser.getRoles());
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
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
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        ShiroUser shiroUser = shiroService.getUserByLoginName(token.getUsername());
        LOG.info("==> normalAuthenticationInfo{}" + shiroUser.toString());
        if (shiroUser == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(shiroUser, shiroUser.getPwd(), getName());
    }

    public void setShiroService(AbstractShiroService shiroService) {
        this.shiroService = shiroService;
    }
}
