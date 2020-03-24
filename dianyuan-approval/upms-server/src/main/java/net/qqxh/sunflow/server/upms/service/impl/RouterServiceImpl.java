package net.qqxh.sunflow.server.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.shiro.utils.MenuUtil;
import net.qqxh.sunflow.server.upms.bean.Router;
import net.qqxh.sunflow.server.upms.mapper.RouterMapper;
import net.qqxh.sunflow.server.upms.service.RouterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class RouterServiceImpl extends ServiceImpl<RouterMapper, Router> implements RouterService {
    @Override
    public List<Router> list2tree() {
        List<Router> list = this.baseMapper.list();
        return formatTree(list);
    }

    @Override
    public List<Router> list2tree(ShiroUser shiroUser) {
        if (shiroUser != null) {
            List<Router> list = listByRole(shiroUser.getRoles());
            return formatTree(list);
        }
        return new ArrayList<>();
    }

    private List<Router> formatTree(List<Router> datalist) {
        Iterator<Router> iterator = datalist.iterator();
        List<Router> topRouter = new ArrayList<Router>();
        while (iterator.hasNext()) {
            Router s = iterator.next();
            //去掉级联关系后需要手动维护这个属性
            boolean getParent = false;
            for (Router p : datalist) {
                if (p.getId().equals(s.getParentId())) {
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
                topRouter.add(s);
            }

        }
        return topRouter;
    }

    @Override
    public List<Router> listByUser(ShiroUser shiroUser) {
        return listByRole(shiroUser.getRoles());
    }

    @Override
    public List<Router> buildMenuByUser(ShiroUser shiroUser) {

        List<Router> list = list2tree(shiroUser);
        return MenuUtil.buildMenuByRouters(list);
    }

    public List<Router> listByRole(Set<String> role) {
        if (role != null && !role.isEmpty()) {
            return this.baseMapper.listByRole(role);
        }
        return new ArrayList<>();
    }
}
