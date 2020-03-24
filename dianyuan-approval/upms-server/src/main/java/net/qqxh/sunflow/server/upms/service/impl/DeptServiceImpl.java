package net.qqxh.sunflow.server.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.upms.bean.Dept;
import net.qqxh.sunflow.server.upms.mapper.DeptMapper;
import net.qqxh.sunflow.server.upms.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈部门信息service〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: DeptServiceImpl.java
 * @date: 2019/5/29 20:46
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public List<Dept> list2tree(Dept dept) {
        QueryWrapper queryWrapper = new QueryWrapper<DeptService>();
        queryWrapper.like("name", dept.getName());
        List<Dept> list = this.list(queryWrapper);
        return formatTree(list);
    }

    private List<Dept> formatTree(List<Dept> datalist) {
        Iterator<Dept> iterator = datalist.iterator();
        List<Dept> topDept = new ArrayList<>();
        while (iterator.hasNext()) {
            Dept s = iterator.next();
            //去掉级联关系后需要手动维护这个属性
            boolean getParent = false;
            for (Dept p : datalist) {
                if (p.getId().equals(s.getPid())) {
                    List sunList = p.getChildren();
                    if (sunList == null) {
                        p.setChildren(new ArrayList());
                    }
                    p.getChildren().add(s);
                    getParent = true;
                    break;
                }
            }
            if (!getParent) {
                topDept.add(s);
            }
        }
        return topDept;
    }
}
