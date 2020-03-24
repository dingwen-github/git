package net.qqxh.sunflow.server.shiro.core;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈授权认证的token解析策略〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: SessionManagerImpl.java
 * @date: 2019/5/29 20:24
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SessionManagerImpl extends DefaultWebSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        String token = httpServletRequest.getHeader("X-Token");

        if (!StringUtils.isEmpty(token)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "X-Token");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;

        } else {
            return super.getSessionId(request, response);
        }
    }
}