package net.qqxh.sunflow.server.shiro.realms;


import net.qqxh.sunflow.server.shiro.core.AdministratorToken;
import net.qqxh.sunflow.server.shiro.utils.MenuUtil;
import net.qqxh.sunflow.server.upms.bean.RoleInfo;
import net.qqxh.sunflow.server.upms.bean.Router;
import net.qqxh.sunflow.server.upms.bean.UserInfo;
import net.qqxh.sunflow.server.upms.service.RoleInfoService;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈shiro超级管理员权限信息提供类〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: AdministratorRealm.java
 * @date: 2019/5/29 20:32
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AdministratorRealm extends AuthorizingRealm {

    private final static Logger LOG = LoggerFactory.getLogger(AdministratorRealm.class);

    RoleInfoService roleInfoService;
    private String administratorUsername;
    private String administratorPassword;

    /**
     * 超级管理员直接授权所有权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (StringUtils.equals(administratorUsername, shiroUser.getLoginName())) {
            List<RoleInfo> list = roleInfoService.list();
            Set<String> role = new HashSet<String>();
            if (list != null) {
                for (RoleInfo roleInfo : list) {
                    role.add(roleInfo.getRoleCode());
                }
            }

            info.setRoles(role);
            return info;
        }
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AdministratorToken;
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
        UserInfo admin = getAdminSetting();
        LOG.info("==> adminAuthenticationInfo{}" + admin.toString());
        if (admin == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(admin, admin.getPwd(), getName());
    }

    private UserInfo getAdminSetting() {
        UserInfo admin = new UserInfo();
        admin.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        admin.setLoginName(administratorUsername);
        admin.setPwd(administratorPassword);
        admin.setName("root");
        admin.setId("1");
        Set<String> role = new HashSet<>();
        role.add("root");
        role.add("admin");
        admin.setRoles(role);
        List<Router> routers = new ArrayList<>();
        routers.add(new Router("1", "-1", "/menu", "upms/menu/index", "", "菜单管理", "菜单管理", "tree", true));
        routers.add(new Router("2", "-1", "/role", "upms/role/index", "", "角色管理", "角色管理", "role", true));
        routers.add(new Router("3", "-1", "/user", "upms/userInfo/index", "", "用户管理", "用户管理", "user", true));
        routers.add(new Router("4", "-1", "/dependence", "upms/dependence", "", "技术栈", "技术栈", "jishu", true));
        routers.add(new Router("5", "-1", "/bpmn", "bpmn/index", "", "流程图", "流程图", "nested", true));
        routers.add(new Router("6", "-1", "/dictionary", "upms/dictionary/index", "", "数据字典", "数据字典", "nested", true));
        routers.add(new Router("7", "-1", "/dept", "upms/dept/index", "", "部门管理", "部门管理", "nested", true));

        admin.setAddRouters(MenuUtil.buildMenuByRouters(routers));
        return admin;
    }

    public void setAdministratorUsername(String administratorUsername) {
        this.administratorUsername = administratorUsername;
    }

    public void setAdministratorPassword(String administratorPassword) {
        this.administratorPassword = administratorPassword;
    }

    public void setRoleInfoService(RoleInfoService roleInfoService) {
        this.roleInfoService = roleInfoService;
    }
}
