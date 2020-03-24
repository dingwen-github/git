package net.qqxh.sunflow.server.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.jedis.JedisPrefix;
import net.qqxh.sunflow.server.shiro.service.AbstractShiroService;
import net.qqxh.sunflow.server.upms.bean.UserRole;
import net.qqxh.sunflow.server.upms.mapper.UserRoleMapper;
import net.qqxh.sunflow.server.upms.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Autowired
    AbstractShiroService shiroService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = JedisPrefix.USER_ROLES, key = "#p0")
    public boolean saveUserRoles(String userId, List<UserRole> userRoleList, String optUserId) {
        Map cmap = new HashMap<>();
        cmap.put("user_id", userId);
        removeByMap(cmap);
        saveBatch(userRoleList);
        if (!StringUtils.equals(userId, optUserId)) {
            shiroService.removeSessionByLoginId(userId);
        }
        return true;
    }
}
