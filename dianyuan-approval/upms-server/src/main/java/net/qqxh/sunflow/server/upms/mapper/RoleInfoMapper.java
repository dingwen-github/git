package net.qqxh.sunflow.server.upms.mapper;

import net.qqxh.sunflow.mapper.SuperMapper;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.upms.bean.RoleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface RoleInfoMapper extends SuperMapper<RoleInfo> {
    Set<String> listRoleCodeByUser(ShiroUser userInfo);
    List<String> listRoutersByRole(String roleCode);
    List<String> listPermissionByRole(String roleCode);
}