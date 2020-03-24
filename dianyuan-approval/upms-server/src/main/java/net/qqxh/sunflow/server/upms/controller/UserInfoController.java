package net.qqxh.sunflow.server.upms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.qqxh.common.validate.annotation.Validate;
import net.qqxh.sunflow.logging.annotation.Log;
import net.qqxh.sunflow.server.common.BaseController;
import net.qqxh.sunflow.server.upms.bean.UserDept;
import net.qqxh.sunflow.server.upms.bean.UserInfo;
import net.qqxh.sunflow.server.upms.bean.UserRole;
import net.qqxh.sunflow.server.upms.service.RouterService;
import net.qqxh.sunflow.server.upms.service.UserDeptService;
import net.qqxh.sunflow.server.upms.service.UserInfoService;
import net.qqxh.sunflow.server.upms.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈用户信息管理〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: UserInfoController.java
 * @date: 2019/5/29 20:45
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController<UserInfo> {
    private final static Logger LOG = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RouterService routerService;
    @Autowired
    UserDeptService userDeptService;


    private final static String rules = "userInfo=required:{code:50000,msg:'用户信息不能为空'};" ;

    @Log("查询用户信息")
    @RequestMapping("/list")
    public Object list(UserInfo userInfo, Page page) {
        QueryWrapper queryWrapper = new QueryWrapper<UserInfo>();
        if (StringUtils.isEmpty(userInfo.getLoginName())) {
            userInfo.setLoginName(null);
        }
        if (StringUtils.isEmpty(userInfo.getName())) {
            userInfo.setName(null);
        }
        queryWrapper.setEntity(userInfo);
        return responseSuccess(userInfoService.page(page, queryWrapper));
    }

    @Log("新增用户信息")
    @RequestMapping("/save")
    @Validate(rules = rules)
    public Object save(String oldLoginName, String userInfo, String userDept) {
        UserInfo obj = JSONObject.parseObject(userInfo, UserInfo.class);
        List<UserDept> list = JSONArray.parseArray(userDept, UserDept.class);
        userInfoService.saveOrUpdateUser(oldLoginName, obj, list);
        LOG.info("save user{}", userInfo);
        return responseSuccess(obj.getId());
    }

    @Log("保存用户角色")
    @RequestMapping("/saveUserRole")
    public Object saveUserRoles(String userId, String userRoles) {
        UserInfo userInfo = (UserInfo) getLoginUser();
        String optUserId = userInfo.getId();
        List<UserRole> userRoleList = JSONObject.parseArray(userRoles, UserRole.class);
        boolean result = userRoleService.saveUserRoles(userId, userRoleList, optUserId);
        LOG.info("save user{}{}", userId, userRoleList);
        return responseSuccess(result);
    }

    @Log("删除用户信息")
    @RequestMapping("/delete")
    public Object delete(String loginName, String id) {
        return responseSuccess(userInfoService.removeUserInfo(loginName, id));
    }

    @Log("查询用户角色")
    @RequestMapping("/listUserRoles")
    public Object listUserRoles(String userId) {
        Map cmap = new HashMap<>();
        cmap.put("user_id", userId);
        return responseSuccess(userRoleService.listByMap(cmap));
    }

    @Log("查询用户角色")
    @RequestMapping("/loadUserDept")
    public Object loadUserDept(String userId) {
        QueryWrapper queryWrapper = new QueryWrapper<UserInfo>();
        queryWrapper.eq("user_id", userId);
        List<UserDept> list = userDeptService.list(queryWrapper);
        return responseSuccess(buildUserDept(list));
    }

    private List<Map<String, Object>> buildUserDept(List<UserDept> list) {
        Map<Integer, Map> map = new HashMap();
        if (list != null) {
            for (UserDept userDept : list) {
                if (map.get(userDept.getDeptId()) == null) {
                    //封装dept对象
                    Map<String, Object> dept = new HashMap();
                    dept.put("id", userDept.getDeptId());
                    Set<String> posts = new HashSet();
                    posts.add(userDept.getPostId());
                    dept.put("posts", posts);
                    map.put(userDept.getDeptId(), dept);
                } else {
                    Map<String, Object> dept = map.get(userDept.getDeptId());
                    Set<String> posts = (Set<String>) dept.get("posts");
                    posts.add(userDept.getPostId());
                }
            }
        }
        return new ArrayList(map.values());
    }

    @Log("查询菜单信息")
    @RequestMapping("/viewMenu")
    public Object viewMenu(String roles) {
        List<String> list = JSONObject.parseArray(roles, String.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setRoles(new HashSet<String>(list));
        if (list.isEmpty()) {
            return responseSuccess(new ArrayList<>());
        }
        return responseSuccess(routerService.list2tree(userInfo));
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public Object checkLoginNameUnique(UserInfo userInfo) {
        return responseSuccess(userInfoService.checkLoginNameUnique(userInfo));
    }
}
