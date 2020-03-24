package net.qqxh.sunflow.server.upms.mapper;

import net.qqxh.sunflow.mapper.SuperMapper;
import net.qqxh.sunflow.server.upms.bean.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends SuperMapper<Permission> {
    List<Permission> listWithRoles();
}
