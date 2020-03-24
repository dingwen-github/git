package net.qqxh.sunflow.server.upms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈角色权限关联〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: RolePermission.java
 * @date: 2019/5/29 20:14
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@TableName("supms_role_permission")
public @Data
class RolePermission {
    @TableId(type = IdType.INPUT)
    String roleCode;
    @TableId(type = IdType.INPUT)
    String permissionId;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
