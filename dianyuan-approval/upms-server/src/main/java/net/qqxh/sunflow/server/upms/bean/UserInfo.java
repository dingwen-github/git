package net.qqxh.sunflow.server.upms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈用户信息表〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: UserInfo.java
 * @date: 2019/5/29 20:17
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@TableName("supms_user_info")
public @Data
class UserInfo implements ShiroUser, Serializable {
    private static final long serialVersionUID = -1373760761780840081L;
    @TableId(type = IdType.AUTO)
    private String id;
    private String pwd;
    @TableField(exist = false)
    private Set<String> roles;
    @TableField(exist = false)
    private List<Router> addRouters;
    private String loginName;
    private String name;
    private String introduction;
    /*头像*/
    private String avatar;

    @Override
    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPwd() {
        return this.pwd;
    }

    @Override
    public Set<String> getRoles() {
        return this.roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Router> getAddRouters() {
        return addRouters;
    }

    public void setAddRouters(List<Router> addRouters) {
        this.addRouters = addRouters;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
