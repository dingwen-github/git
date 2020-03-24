package net.qqxh.sunflow.server.common;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈Controller基类，提供公用方法〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: BaseController.java
 * @date: 2019/5/29 20:40
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BaseController<T> {

    public Object responseSuccess(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 20000);
        jsonObject.put("data", data);
        return jsonObject;
    }

    public Object responseFail(Object message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 50000);
        jsonObject.put("message", message);
        return jsonObject;
    }

    public Object responseTokenInvalid(Object message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 50014);
        jsonObject.put("message", message);
        return jsonObject;
    }

    public Object responseUnauthorized(Object message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 403);
        jsonObject.put("message", message);
        return jsonObject;
    }

    public ShiroUser getLoginUser() {
        ShiroUser jfUserSimple = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return jfUserSimple;
    }


}
