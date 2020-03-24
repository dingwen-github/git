package net.qqxh.sunflow.server.shiro.filter;

import net.qqxh.sunflow.server.shiro.oauth2.OAuth2Token;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈shiro自定义Oauth2认证过滤器〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: Oauth2AuthorizationFilter.java
 * @date: 2019/5/29 20:27
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
public class Oauth2AuthorizationFilter extends AuthenticatingFilter {

    private String authcCodeParam = "code";

    private String failureUrl;
    private String successUrl = "/oauth2/sussucc";

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //从url中拿到auth code
        String code = httpRequest.getParameter(authcCodeParam);
        String path = httpRequest.getServletPath();
        String type = path.substring(path.lastIndexOf("/") + 1);
        //用auth code创建auth2Token
        return new OAuth2Token(code, type);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    public String getSuccessUrl() {
        return successUrl;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //首先判定有没有error，有的话直接重定向到失败页面
        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        //如果服务端返回了错误
        if (!StringUtils.isEmpty(error)) {
            WebUtils.issueRedirect(request, response, failureUrl + "/#/login?error=" + error + "error_description=" + errorDescription);
            return false;
        }

        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            if (StringUtils.isEmpty(request.getParameter(authcCodeParam))) {
                //如果用户没有身份验证，且没有auth code，则重定向到服务端授权
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        }
        //执行父类的登录逻辑，调用Subject.login登录
        return executeLogin(request, response);
    }

    /**
     * 登录成功
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        return false;
    }

    /**
     * 登录失败
     *
     * @param token
     * @param ae
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl + "/#/login?error=500");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
