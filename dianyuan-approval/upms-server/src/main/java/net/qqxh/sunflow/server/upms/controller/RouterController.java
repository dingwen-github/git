package net.qqxh.sunflow.server.upms.controller;

import net.qqxh.sunflow.server.common.BaseController;
import net.qqxh.sunflow.server.upms.bean.Permission;
import net.qqxh.sunflow.server.upms.bean.Router;
import net.qqxh.sunflow.server.upms.service.PermissionService;
import net.qqxh.sunflow.server.upms.service.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈路由控制器〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: RouterController.java
 * @date: 2019/5/29 20:44
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping("/router")
public class RouterController extends BaseController {
    @Autowired
    RouterService routerService;
    @Autowired
    PermissionService permissionService;

    @RequestMapping("/save")
    public Object save(Router router) {
        router.setTitle(router.getName());
        routerService.save(router);
        return responseSuccess(router.getId());
    }

    @RequestMapping("/update")
    public Object update(Router router) {
        router.setTitle(router.getName());
        return responseSuccess(routerService.saveOrUpdate(router));
    }

    @RequestMapping("/list")
    public Object list() {
        return responseSuccess(routerService.list2tree());

    }

    @RequestMapping("/delete")
    public Object delete(String id) {
        if (routerService.removeById(id)) {
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("parentRouter", id);
            permissionService.removeByMap(columnMap);
            return responseSuccess(id);
        }
        return responseFail(id);
    }

    @RequestMapping("/deletePermission")
    public Object deletePermission(String id) {
        return responseSuccess(permissionService.removeById(id));
    }

    @RequestMapping("/savePermission")
    public Object savePermission(Permission permission) {
        permissionService.save(permission);
        return responseSuccess(permission.getId());
    }
}
