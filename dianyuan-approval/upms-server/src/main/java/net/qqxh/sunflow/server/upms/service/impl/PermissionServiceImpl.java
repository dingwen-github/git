package net.qqxh.sunflow.server.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.upms.bean.Permission;
import net.qqxh.sunflow.server.upms.mapper.PermissionMapper;
import net.qqxh.sunflow.server.upms.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈权限信息service〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: PermissionServiceImpl.java
 * @date: 2019/5/29 20:46
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> listWithRoles() {
        return this.baseMapper.listWithRoles();
    }
}
