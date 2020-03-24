package net.qqxh.sunflow.server.upms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈路由表〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: Router.java
 * @date: 2019/5/29 20:16
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@TableName("supms_router_info")
public @Data
class Router implements Serializable {
    @TableId(type = IdType.AUTO)
    String id;
    @TableField("parentId")
    String parentId;
    String path;
    String component;
    String redirect;
    String name;

    String title;
    String icon;
    Integer orders;
    @TableField(exist = false)
    Boolean checked;

    @TableField(exist = false)
    List<Permission> permissions;
    @TableField(exist = false)
    List<Router> children;
    public Router() {
    }

    public Router(String id, String parentId, String path, String component, String redirect, String name, String title, String icon, Boolean checked) {
        this.id = id;
        this.parentId = parentId;
        this.path = path;
        this.component = component;
        this.redirect = redirect;
        this.name = name;
        this.title = title;
        this.icon = icon;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Router> getChildren() {
        return children;
    }

    public void setChildren(List<Router> children) {
        this.children = children;
    }
}
