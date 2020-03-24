package net.qqxh.sunflow.server.shiro.config;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.SecurityFilter;
import net.qqxh.sunflow.server.shiro.core.SessionManagerImpl;
import net.qqxh.sunflow.server.shiro.filter.CustomRolesAuthorizationFilter;
import net.qqxh.sunflow.server.shiro.filter.Oauth2AuthorizationFilter;
import net.qqxh.sunflow.server.shiro.oauth2.AuthRequestFactory;
import net.qqxh.sunflow.server.shiro.realms.AdministratorRealm;
import net.qqxh.sunflow.server.shiro.realms.CasRealm;
import net.qqxh.sunflow.server.shiro.realms.NormalRealm;
import net.qqxh.sunflow.server.shiro.realms.Oauth2Realm;
import net.qqxh.sunflow.server.shiro.service.AbstractShiroService;
import net.qqxh.sunflow.server.upms.service.RoleInfoService;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈shiro安全控制规则配置〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: ShiroConfiguration.java
 * @date: 2019/5/29 20:21
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
public class ShiroConfiguration {

    @Autowired
    AbstractShiroService shiroService;
    @Autowired
    RoleInfoService roleInfoService;
    /**
     * 项目工程路径
     */
    @Value("${cas.project.url}")
    private String projectUrl;

    /**
     * 项目cas服务路径
     */
    @Value("${cas.server.url}")
    private String casServerUrl;
    @Value("${cas.enable}")
    private Boolean casEnable;
    /**
     * 客户端名称
     */
    @Value("${cas.client-name}")
    private String clientName;
    @Value("${cas.webapp.url}")
    private String webappUrl;
    @Value("${administrator.username}")
    private String administratorUsername;
    @Value("${administrator.password}")
    private String administratorPassword;
    @Autowired
    AuthRequestFactory authRequestFactory;
    @Autowired(required = false)
    Config config;

    @Bean
    public NormalRealm normalRealm() {
        NormalRealm normalRealm = new NormalRealm();
        normalRealm.setShiroService(shiroService);
        return normalRealm;
    }

    @Bean
    public Oauth2Realm Oauth2Realm() {
        Oauth2Realm realm = new Oauth2Realm();
        realm.setShiroService(shiroService);
        realm.setAuthRequestFactory(authRequestFactory);
        return realm;
    }

    @Bean
    public AdministratorRealm AdminRealm() {
        AdministratorRealm realm = new AdministratorRealm();
        realm.setRoleInfoService(roleInfoService);
        realm.setAdministratorUsername(administratorUsername);
        realm.setAdministratorPassword(administratorPassword);
        return realm;
    }

    @Bean
    public CasRealm casRealm() {
        CasRealm realm = new CasRealm();
        // 使用自定义的realm
        realm.setClientName(clientName);
        realm.setCachingEnabled(false);
        //暂时不使用缓存
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthorizationCachingEnabled(false);
        return realm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        List<Realm> list = new ArrayList<>();
        list.add(AdminRealm());
        list.add(normalRealm());

        list.add(Oauth2Realm());
        if (casEnable) {
            list.add(casRealm());
        }
        //Shiro多个Realm认证及授权时，执行doMultiRealmAuthentication，
        // 通过realm.supports(token)，判断是否走当前的认证
        securityManager.setRealms(list);
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        SessionManager sessionManager = new SessionManagerImpl();
        return sessionManager;
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(604800);

        rememberMeManager.setCookie(simpleCookie);
        return rememberMeManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> filters = new HashMap<String, Filter>();
        if (casEnable) {
            getCasFilters(filters);
        }
        filters.put("roles", new CustomRolesAuthorizationFilter());


        Oauth2AuthorizationFilter oauth2AuthorizationFilter = new Oauth2AuthorizationFilter();
        oauth2AuthorizationFilter.setFailureUrl(webappUrl);
        filters.put("oauth2Authc", oauth2AuthorizationFilter);

        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/");
        //错误页面，无权限跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        /*过滤器，分为授权相关的和认证相关的*/
        Map<String, String> filterChainDefinitionMap = shiroService.getFilterChainDefinitionMap();

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroService.setShiroFilterFactoryBean(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    private void getCasFilters(Map<String, Filter> filters) {
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setConfig(config);
        securityFilter.setClients(clientName);
        filters.put("securityFilter", securityFilter);
        //cas 认证后回调拦截器
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config);
        callbackFilter.setDefaultUrl(projectUrl);
        filters.put("callbackFilter", callbackFilter);
     /*   // 注销 拦截器
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(config);
        logoutFilter.setCentralLogout(true);
        logoutFilter.setLocalLogout(true);
        logoutFilter.setDefaultUrl(projectUrl + "/callback?client_name=" + clientName);
        filters.put("logout",logoutFilter);*/
    }

    /**
     * 注解支持，访问授权动态拦截，不然不会执行doGetAuthenticationInfo
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}