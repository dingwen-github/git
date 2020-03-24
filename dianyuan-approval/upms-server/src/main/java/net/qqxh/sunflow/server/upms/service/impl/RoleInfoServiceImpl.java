package net.qqxh.sunflow.server.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.jedis.JedisPrefix;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.upms.bean.RoleInfo;
import net.qqxh.sunflow.server.upms.bean.RolePermission;
import net.qqxh.sunflow.server.upms.bean.RoleRouter;
import net.qqxh.sunflow.server.upms.mapper.RoleInfoMapper;
import net.qqxh.sunflow.server.upms.service.RoleInfoService;
import net.qqxh.sunflow.server.upms.service.RolePermissionService;
import net.qqxh.sunflow.server.upms.service.RoleRouterService;
import net.qqxh.sunflow.server.upms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈角色信息service〉<br>
 * 〈功能详细描述〉
 *
 * @author Jwy
 * @fileName: RoleInfoServiceImpl.java
 * @date: 2019/5/29 20:46
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements RoleInfoService {
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    RoleRouterService roleRouterService;
    @Autowired
    UserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean savePermissionsAndRouters(String roleCode, List<RolePermission> permissions, List<RoleRouter> routers) {
        rolePermissionService.removeById(roleCode);
        roleRouterService.removeById(roleCode);
        if (permissions != null && permissions.size() > 0) {
            rolePermissionService.saveOrUpdateBatch(permissions);
        }
        if (routers != null && routers.size() > 0){
            roleRouterService.saveOrUpdateBatch(routers);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRolePerAndRouter(String roleCode) {
        rolePermissionService.removeById(roleCode);
        return roleRouterService.removeById(roleCode);
    }
    @Override
    @Cacheable(value = JedisPrefix.USER_ROLES, key = "#userInfo.id")
    public Set<String> listRoleCodeByUser(ShiroUser userInfo) {
        return baseMapper.listRoleCodeByUser(userInfo);
    }
    @Override
    public List<String> listRoutersByRole(String roleCode) {
        return baseMapper.listRoutersByRole(roleCode);
    }

    @Override
    public List<String> listPermissionByRole(String roleCode) {
        return baseMapper.listPermissionByRole(roleCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = JedisPrefix.USER_ROLES,allEntries = true)
    public int deleteRoleByCode(String roleCode) {
        //删除关联数据
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("role_code",roleCode);
        rolePermissionService.remove(queryWrapper);
        roleRouterService.remove(queryWrapper);
        userRoleService.remove(queryWrapper);
        return baseMapper.deleteById(roleCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleSetting(RoleInfo fRoleinfo, List<RolePermission> permissions, List<RoleRouter> routers) {
        super.saveOrUpdate(fRoleinfo);
        return this.savePermissionsAndRouters(fRoleinfo.getRoleCode(),permissions,routers);
    }


}
