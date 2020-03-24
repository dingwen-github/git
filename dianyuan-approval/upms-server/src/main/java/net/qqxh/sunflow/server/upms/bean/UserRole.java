package net.qqxh.sunflow.server.upms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈用户角色关联表〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: UserRole.java
 * @date: 2019/5/29 20:17
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Builder
@TableName("supms_user_role")
public @Data
class UserRole {
    @TableId(type = IdType.INPUT)
    String userId;
    @TableId(type = IdType.INPUT)
    String roleCode;
    public UserRole() {
    }

    public UserRole(String userId, String roleCode) {
        this.userId = userId;
        this.roleCode = roleCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
