package net.qqxh.sunflow.server.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.server.upms.bean.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> listWithRoles();
}
