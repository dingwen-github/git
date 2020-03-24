package net.qqxh.sunflow.server.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.upms.bean.Router;

import java.util.List;

public interface RouterService extends IService<Router> {

    List<Router> list2tree();

    List<Router> listByUser(ShiroUser shiroUser);
    List<Router> list2tree(ShiroUser shiroUser);
    List<Router> buildMenuByUser(ShiroUser shiroUser);
}
