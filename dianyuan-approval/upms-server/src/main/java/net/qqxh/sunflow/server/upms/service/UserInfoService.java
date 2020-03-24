package net.qqxh.sunflow.server.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.server.upms.bean.UserDept;
import net.qqxh.sunflow.server.upms.bean.UserInfo;

import java.util.List;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈用户信息serevice〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: UserInfoService.java
 * @date: 2019/5/29 20:49
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 校验用户名称是否唯一
     *
     * @param userInfo 登录名称
     * @return 结果
     */
    boolean checkLoginNameUnique(UserInfo userInfo);

    /**
     * 保存或更新
     * @param userInfo
     * @param userDeptList
     * @return
     */
    boolean saveOrUpdateUser(String oldLoginName ,UserInfo userInfo, List<UserDept> userDeptList);

    /**
     *
     * @param loginName
     * @return
     */
    UserInfo getUserByLoginName(String loginName);

    boolean removeUserInfo(String loginName,String id);
}
