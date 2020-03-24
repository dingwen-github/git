package net.qqxh.sunflow.server.shiro.utils;

import net.qqxh.sunflow.server.upms.bean.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单数据处理工具
 * @author zhs
 */
public class MenuUtil {
    private MenuUtil() {
    }

    public static List<Router> buildMenuByRouters(List<Router> list ){
        List<Router> response = new ArrayList();
        for (Router router : list) {
            /*一级中没有子菜单的*/
            if (router.getChildren() == null || router.getChildren().size() == 0) {
                Router top = new Router();
                top.setPath(router.getPath());
                List children = new ArrayList();
                router.setPath("");
                children.add(router);
                top.setComponent("layout/Layout");
                top.setTitle(router.getTitle());
                top.setParentId("-1");
                top.setChildren(children);
                response.add(top);
            } else {
                router.setComponent("layout/Layout");
                response.add(router);
            }
        }
        return response;
    }
}
