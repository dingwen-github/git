package net.qqxh.sunflow.server.upms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈角色路由关联表〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: RoleRouter.java
 * @date: 2019/5/29 20:15
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@TableName("supms_role_router")
public @Data
class RoleRouter {
    @TableId(type = IdType.INPUT)
    String roleCode;
    @TableId(type = IdType.INPUT)
    String routerId;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }
}
