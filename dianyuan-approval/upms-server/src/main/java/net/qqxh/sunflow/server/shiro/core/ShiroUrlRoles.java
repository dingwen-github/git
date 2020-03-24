package net.qqxh.sunflow.server.shiro.core;

import java.util.Set;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈权限角色关联关系〉<br>
 * 〈维护了某个url所需要的角色信息数据〉
 *
 * @author jason
 * @fileName: ShiroUrlRoles.java
 * @date: 2019/5/29 20:25
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ShiroUrlRoles {
    String getUrl();

    /**
     * url所需要的角色列表
     *
     * @return
     */
    Set<String> getRoles();
}
