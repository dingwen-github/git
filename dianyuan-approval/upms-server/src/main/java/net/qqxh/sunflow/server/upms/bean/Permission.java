package net.qqxh.sunflow.server.upms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.qqxh.sunflow.server.shiro.core.ShiroUrlRoles;

import java.io.Serializable;
import java.util.Set;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈权限bean〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: Permission.java
 * @date: 2019/5/29 20:14
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@TableName("supms_permission")
public @Data
class Permission implements ShiroUrlRoles, Serializable {
    @TableId(type = IdType.AUTO)
    String id;
    String url;
    String name;
    @TableField("parentRouter")
    Long parentRouter;
    @TableField(exist = false)
    Boolean checked;
    @TableField(exist = false)
    Set<String> roles;

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentRouter() {
        return parentRouter;
    }

    public void setParentRouter(Long parentRouter) {
        this.parentRouter = parentRouter;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
