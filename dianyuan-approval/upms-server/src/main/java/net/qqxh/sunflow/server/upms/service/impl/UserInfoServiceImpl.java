package net.qqxh.sunflow.server.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.jedis.JedisPrefix;
import net.qqxh.sunflow.server.upms.bean.ThirdAuthUser;
import net.qqxh.sunflow.server.upms.bean.UserDept;
import net.qqxh.sunflow.server.upms.bean.UserInfo;
import net.qqxh.sunflow.server.upms.bean.UserRole;
import net.qqxh.sunflow.server.upms.mapper.UserInfoMapper;
import net.qqxh.sunflow.server.upms.service.ThirdAuthUserService;
import net.qqxh.sunflow.server.upms.service.UserDeptService;
import net.qqxh.sunflow.server.upms.service.UserInfoService;
import net.qqxh.sunflow.server.upms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (C), 2019/6/22, sunflow开发团队
 * 〈用户管理〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: UserInfoServiceImpl.java
 * @date: 2019/6/22 12:02
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    UserDeptService userDeptService;
    @Autowired
    ThirdAuthUserService thirdAuthUserService;
    @Autowired
    UserRoleService userRoleService;

    /**
     * 校验用户名称是否唯一
     *
     * @param userInfo 用户名
     * @return 操作结果
     */
    @Override
    public boolean checkLoginNameUnique(UserInfo userInfo) {
        int count = baseMapper.checkLoginNameUnique(userInfo);
        return count == 0;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = JedisPrefix.USER_INFO, key = "#p0")
    public boolean saveOrUpdateUser(String oldLoginName, UserInfo userInfo, List<UserDept> userDeptList) {
        saveOrUpdate(userInfo);
        QueryWrapper<ThirdAuthUser> thirdAuthWrapper = new QueryWrapper<>();
        thirdAuthWrapper.eq("user_id", userInfo.getId());
        ThirdAuthUser thirdAuthUser = thirdAuthUserService.getOne(thirdAuthWrapper);
        if (thirdAuthUser != null) {
            thirdAuthUser.setLoginName(userInfo.getLoginName());
            thirdAuthUserService.saveOrUpdate(thirdAuthUser);
        }
        QueryWrapper<UserDept> userDeptWrapper = new QueryWrapper<>();
        userDeptWrapper.eq("user_id", userInfo.getId());
        userDeptService.remove(userDeptWrapper);
        if (userDeptList != null) {
            for (UserDept dept : userDeptList) {
                dept.setUserId(userInfo.getId());
            }
            userDeptService.saveBatch(userDeptList);
        }
        return true;
    }

    @Override
    @Cacheable(value = JedisPrefix.USER_INFO, key = "#p0")
    public UserInfo getUserByLoginName(String loginName) {
        QueryWrapper<UserInfo> userInfoWrapper = new QueryWrapper<UserInfo>();
        userInfoWrapper.eq("login_name", loginName);
        return super.getOne(userInfoWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {@CacheEvict(value = {JedisPrefix.USER_INFO}, key = "#p0"), @CacheEvict(value = {JedisPrefix.USER_ROLES}, key = "#p1")})
    public boolean removeUserInfo(String loginName, String id) {
        QueryWrapper<ThirdAuthUser> thirdAuthWrapper = new QueryWrapper<>();
        thirdAuthWrapper.eq("login_name", loginName);
        thirdAuthUserService.remove(thirdAuthWrapper);
        QueryWrapper<UserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("user_id", id);
        userRoleService.remove(userRoleWrapper);
        QueryWrapper<UserInfo> userInfoWrapper = new QueryWrapper<>();
        userInfoWrapper.eq("login_name", loginName);
        return super.remove(userInfoWrapper);
    }

}
