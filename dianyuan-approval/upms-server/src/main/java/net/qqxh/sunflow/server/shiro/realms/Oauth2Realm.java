package net.qqxh.sunflow.server.shiro.realms;

import io.buji.pac4j.realm.Pac4jRealm;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import net.qqxh.sunflow.server.shiro.oauth2.AuthRequestFactory;
import net.qqxh.sunflow.server.shiro.oauth2.OAuth2Token;
import net.qqxh.sunflow.server.shiro.oauth2.SfAuthRequest;
import net.qqxh.sunflow.server.shiro.service.AbstractShiroService;
import net.qqxh.sunflow.server.upms.bean.ThirdAuthUser;
import net.qqxh.sunflow.server.upms.service.ThirdAuthUserService;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈Oauth2认证权限信息提供类〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: Oauth2Realm.java
 * @date: 2019/5/29 20:32
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Oauth2Realm extends Pac4jRealm {
    @Autowired
    AbstractShiroService shiroService;
    private String clientName;
    @Autowired
    AuthRequestFactory authRequestFactory;
    @Autowired
    ThirdAuthUserService thirdAuthUserService;

    private final static Logger LOG = LoggerFactory.getLogger(Oauth2Realm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
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
        final OAuth2Token oAuth2Token = (OAuth2Token) authenticationToken;
        final String code = (String) oAuth2Token.getCredentials();
        LOG.info("==> Oauth2AuthenticationCode" + code);
        SfAuthRequest sfAuthRequest = authRequestFactory.getAuthRequest(oAuth2Token.getSource());
        AuthRequest authRequest = sfAuthRequest.getAuthRequest();
        AuthResponse login = authRequest.login(code);
        //通过登陆信息获取用户信息
        ShiroUser shiroUser = null;
        if (0 == login.getCode()) {
            ThirdAuthUser authUser = (ThirdAuthUser) login.getData();
            shiroUser = thirdAuthUserService.getOrRegisterUser(authUser);
        }
        LOG.info("==> Oauth2AuthenticationLogin" + login);
        final PrincipalCollection principalCollection = new SimplePrincipalCollection(shiroUser, getName());
        if (shiroUser == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(principalCollection, code);
    }

    public void setShiroService(AbstractShiroService shiroService) {
        this.shiroService = shiroService;
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

    public void setAuthRequestFactory(AuthRequestFactory authRequestFactory) {
        this.authRequestFactory = authRequestFactory;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}