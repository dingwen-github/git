package net.qqxh.sunflow.server.upms.controller;


import net.qqxh.sunflow.server.upms.bean.Dept;
import net.qqxh.sunflow.server.upms.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import net.qqxh.sunflow.server.common.BaseController;

import java.time.LocalDateTime;


/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈部门信息控制器〉<br>
 * 〈功能详细描述〉
 *
 * @author jwy
 * @fileName: DeptController.java
 * @date: 2019/5/29 20:44
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {
    @Autowired
    DeptService deptService;

    @RequestMapping("/save")
    public Object save(Dept dept) {

        if (dept.getId() != null) {
            dept.setUpdateTime(LocalDateTime.now());
        } else {
            dept.setCreateTime(LocalDateTime.now());
        }
        deptService.saveOrUpdate(dept);
        return responseSuccess(dept.getId());
    }

    @RequestMapping("/list")
    public Object list(Dept dept) {
        return responseSuccess(deptService.list2tree(dept));
    }

    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable("id") String id) {
        return responseSuccess(deptService.removeById(id));
    }
}

