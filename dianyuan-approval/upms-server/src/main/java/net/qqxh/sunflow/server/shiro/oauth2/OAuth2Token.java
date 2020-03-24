package net.qqxh.sunflow.server.shiro.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈oauth2认证token〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: OAuth2Token.java
 * @date: 2019/5/29 20:28
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OAuth2Token implements AuthenticationToken {

    public OAuth2Token(String authCode) {
        this.authCode = authCode;
    }

    public OAuth2Token(String authCode, String source) {
        this.authCode = authCode;
        this.source = source;
    }

    //授权码
    private String authCode;
    /**
     * token真正授权用到
     */
    private String principal;


    private String source;

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return authCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
