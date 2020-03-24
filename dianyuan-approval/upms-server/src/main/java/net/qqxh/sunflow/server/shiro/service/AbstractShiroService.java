package net.qqxh.sunflow.server.shiro.service;

import net.qqxh.sunflow.server.shiro.core.ShiroUrlRoles;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈shiro认证用户信息获取service模板〉<br>
 * 〈使用的是模板方法模式〉
 *
 * @author jason
 * @fileName: AbstractShiroService.java
 * @date: 2019/5/29 20:34
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class AbstractShiroService {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractShiroService.class);
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    boolean casEnable;
    /**
     * 加载用户信息并封装该用户的角色信息
     *
     * @param loginName
     * @return
     */
    public abstract ShiroUser getUserByLoginName(String loginName);


    /**
     * 加载用户信息角色信息
     *
     * @param shiroUser
     * @return
     */
    public abstract Set<String> getRolesByUserinfo(ShiroUser shiroUser);
    /**
     * 获取系统所有url权限角色信息
     *
     * @return
     */
    public abstract List<ShiroUrlRoles> getAllUrlRolesList();

    /**
     * 更新系统权限缓存数据方法。后台配置改变之后需要调用该方法进行刷新缓存信息
     */
    public void updatePermission() {

        synchronized (shiroFilterFactoryBean) {

            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                        .getObject();
            } catch (Exception e) {
                throw new RuntimeException(
                        "get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(getFilterChainDefinitionMap());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
            LOG.info("更新权限成功！！");
        }
    }

    public void setShiroFilterFactoryBean(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        this.shiroFilterFactoryBean = shiroFilterFactoryBean;
    }

    /**
     * 获取系统所有权限信息
     *
     * @return
     */
    public Map<String, String> getFilterChainDefinitionMap() {
        List<ShiroUrlRoles> list = getAllUrlRolesList();
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        if (list != null) {
            for (ShiroUrlRoles shiroUrlRoles : list) {
                Set<String> roles = shiroUrlRoles.getRoles();
                if (roles != null && !roles.isEmpty()) {
                    filterChainDefinitionMap.put(shiroUrlRoles.getUrl(), "roles" + roles.toString());
                }
            }
        }
        if (casEnable) {
            filterChainDefinitionMap.put("/callback", "callbackFilter");
            filterChainDefinitionMap.put("/user/cas/login", "securityFilter");
            filterChainDefinitionMap.put("/logout", "logout");
        }
        filterChainDefinitionMap.put("/oauth2-login/*", "oauth2Authc");
        filterChainDefinitionMap.put("/**", "anon");
        return filterChainDefinitionMap;
    }

    /**
     * 强制注销用户
     * @param id
     * @return
     */
    public void removeSessionByLoginId(String id){
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
        SessionDAO sessionDAO=sessionManager.getSessionDAO();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for(Session session:sessions){
            PrincipalCollection principalCollection=(PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principalCollection != null ) {
                ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
                if (StringUtils.equals(id, shiroUser.getId())) {
                    try {
                        sessionDAO.delete(session);
                    } catch (Exception e) {
                        LOG.error("", e);
                    }

                }
            }
        }
    }

    public void setCasEnable(boolean casEnable) {
        this.casEnable = casEnable;
    }
}
