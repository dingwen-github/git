package net.qqxh.sunflow.server.shiro.controller;

import me.zhyd.oauth.request.AuthRequest;
import net.qqxh.sunflow.server.common.BaseController;
import net.qqxh.sunflow.server.shiro.core.AdministratorToken;
import net.qqxh.sunflow.server.shiro.oauth2.AuthRequestFactory;
import net.qqxh.sunflow.server.upms.bean.UserInfo;
import net.qqxh.sunflow.server.upms.service.RouterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈应用登录授权控制〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: MainController.java
 * @date: 2019/5/29 20:22
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping()
public class MainController extends BaseController {
    private final static Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Value("${cas.webapp.url}")
    private String webAppUrl;
    @Value("${administrator.username}")
    private String administratorUsername;
    @Autowired
    AuthRequestFactory authRequestFactory;
    @Autowired
    RouterService routerService;

    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        Subject user = SecurityUtils.getSubject();
        response.sendRedirect(webAppUrl);
    }

    @GetMapping("/user/cas/login")
    public void caslogin(HttpServletResponse response) throws IOException {
        Subject user = SecurityUtils.getSubject();
        response.sendRedirect(webAppUrl + "#cas/" + user.getSession().getId());
    }

    @GetMapping("/to/oauth2/{type}")
    public void oauth2login(HttpServletResponse response, @PathVariable("type") String type) throws IOException {
        AuthRequest authRequest = authRequestFactory.getAuthRequest(type).getAuthRequest();
        LOG.info("==> oauth2login sendRedirect{}", authRequest.toString());
        response.sendRedirect(authRequest.authorize());
    }

    @GetMapping("/oauth2/sussucc")
    public void oauth2sussucc(HttpServletResponse response) throws IOException {
        Subject user = SecurityUtils.getSubject();
        response.sendRedirect(webAppUrl + "#oauth2/" + user.getSession().getId());
    }

    @GetMapping("/login")
    public Object login() {
        return responseTokenInvalid("登录失效请重新登录");
    }

    /**
     * POST 登录 shiro 写法
     *
     * @param username 用户名
     * @param password 密码
     * @return {Object}
     */
    @PostMapping("/login")
/*
    @Validate(rules = "username=required:{code:50000,msg:'请输入登录名'};password=required:{code:50000,msg:'请输入密码'}")
*/
    public Object loginPost(String username, String password, String captcha,
                            @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe) {
        String sessionId;
        Subject user = SecurityUtils.getSubject();
        AdministratorToken administratorToken = new AdministratorToken(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            token.setRememberMe(false);
            user.login(StringUtils.equals(administratorUsername, username) ? administratorToken : token);
            sessionId = (String) user.getSession().getId();
        } catch (IncorrectCredentialsException e) {
            /*认证失败*/
            return responseFail("用户名或密码不正确");
        } catch (AuthenticationException e) {
            return responseFail("用户不存在");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("token", sessionId);
        return responseSuccess(map);
    }

    @GetMapping("/info")
    public Object getInfo() {
        UserInfo userInfo = (UserInfo) getLoginUser();
        if (userInfo != null&&!StringUtils.equals(userInfo.getLoginName(),administratorUsername)) {
            userInfo.setAddRouters(routerService.buildMenuByUser(userInfo));
        }
        return responseSuccess(userInfo);
    }

    @PostMapping("/logout")
    public Object logout() {
        SecurityUtils.getSubject().logout();
        return responseSuccess("success");
    }

    @ResponseBody
    @RequestMapping("/403")
    public Object unauthorized() {
        return responseUnauthorized("没有权限");
    }
}
