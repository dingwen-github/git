package net.qqxh.sunflow.server.shiro.service.impl;


import net.qqxh.sunflow.server.shiro.core.ShiroUrlRoles;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.shiro.service.AbstractShiroService;
import net.qqxh.sunflow.server.upms.bean.UserInfo;
import net.qqxh.sunflow.server.upms.service.PermissionService;
import net.qqxh.sunflow.server.upms.service.RoleInfoService;
import net.qqxh.sunflow.server.upms.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈shiro认证用户信息获取service模板实现〉<br>
 * 〈用于对shiro提供服务〉
 *
 * @author jason
 * @fileName: ShiroServiceImpl.java
 * @date: 2019/5/29 20:34
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("shiroService")
public class ShiroServiceImpl extends AbstractShiroService {
    private final Logger LOGGER = LoggerFactory.getLogger(ShiroServiceImpl.class);
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleInfoService fRoleInfoService;

    public ShiroServiceImpl(@Value("${cas.enable}") Boolean casEnable) {
        super();
        super.setCasEnable(casEnable);
    }

    @Override
    public ShiroUser getUserByLoginName(String loginName) {
        UserInfo gotUserInfo = null;
        try {
            gotUserInfo = userInfoService.getUserByLoginName(loginName);
            if (gotUserInfo != null) {
                Set<String> roles = getRolesByUserinfo(gotUserInfo);
                gotUserInfo.setRoles(roles);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }

        return gotUserInfo;
    }

    @Override
    public Set<String> getRolesByUserinfo(ShiroUser shiroUser) {
        return fRoleInfoService.listRoleCodeByUser(shiroUser);
    }

    /**
     * 拿到系统中所有url对应的角色信息
     *
     * @return
     */
    @Override
    public List<ShiroUrlRoles> getAllUrlRolesList() {
        List list = permissionService.listWithRoles();
        return list;
    }
}
