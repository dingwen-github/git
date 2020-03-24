package net.qqxh.sunflow.server.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.server.upms.bean.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    boolean saveUserRoles(String userId,List<UserRole> userRoleList,String optUserId);

}
