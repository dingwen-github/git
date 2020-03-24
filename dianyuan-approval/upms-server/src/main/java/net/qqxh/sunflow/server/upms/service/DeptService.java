package net.qqxh.sunflow.server.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.server.upms.bean.Dept;

import java.util.List;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈部门信息service接口〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: DeptService.java
 * @date: 2019/5/29 20:48
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface DeptService extends IService<Dept> {

    List<Dept> list2tree(Dept dept);
}
