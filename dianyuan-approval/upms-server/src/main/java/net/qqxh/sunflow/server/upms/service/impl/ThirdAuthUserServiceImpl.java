package net.qqxh.sunflow.server.upms.service.impl;

import net.qqxh.sunflow.server.shiro.service.AbstractShiroService;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.upms.bean.ThirdAuthUser;
import net.qqxh.sunflow.server.upms.mapper.ThirdAuthUserMapper;
import net.qqxh.sunflow.server.upms.service.ThirdAuthUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.upms.bean.UserInfo;
import net.qqxh.sunflow.server.upms.bean.UserRole;
import net.qqxh.sunflow.server.upms.service.UserInfoService;
import net.qqxh.sunflow.server.upms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈第三方权限登陆信息表〉<br>
 * 〈第三方权限登陆信息表 用于记录第三方系统登陆本系统中记录和自己用户关联的信息。 服务实现类〉
 *
 * @author jwy
 * @fileName: ThirdAuthUserServiceImpl.java
 * @date: 2019/5/29 20:47
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class ThirdAuthUserServiceImpl extends ServiceImpl<ThirdAuthUserMapper, ThirdAuthUser> implements ThirdAuthUserService {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    AbstractShiroService shiroService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShiroUser getOrRegisterUser(ThirdAuthUser user) {
        String thirdId = user.getSource() + "_" + user.getId();
        ThirdAuthUser dbAuthUser = super.getById(thirdId);
        if (dbAuthUser != null) {
            return shiroService.getUserByLoginName(dbAuthUser.getLoginName());
        } else {
            //注册新用户
            UserInfo userInfo = new UserInfo();
            userInfo.setLoginName(user.getSource() + user.getUsername());

            userInfo.setName(user.getUsername());
            userInfo.setPwd("00000");
            userInfo.setAvatar(user.getAvatar());
            userInfoService.save(userInfo);
            user.setUserId(userInfo.getId());
            /*保证id不会重复*/
            user.setId(thirdId);
            user.setLoginName(user.getSource() + user.getUsername());
            userRoleService.save(UserRole.builder().roleCode("thirdPart").userId(userInfo.getId()).build());
            save(user);
            return shiroService.getUserByLoginName(userInfo.getLoginName());
        }
    }

}
