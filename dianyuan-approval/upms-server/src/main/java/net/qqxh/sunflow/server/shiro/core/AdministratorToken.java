package net.qqxh.sunflow.server.shiro.core;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈超级管理员shiro token〉<br>
 * 〈超级管理员权限体系固化，单独一套认证体系〉
 *
 * @author jason
 * @fileName: AdministratorToken.java
 * @date: 2019/5/29 20:23
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AdministratorToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
    private String username;
    private char[] password;
    private boolean rememberMe;
    private String host;

    public AdministratorToken() {
        this.rememberMe = false;
    }

    public AdministratorToken(String username, char[] password) {
        this(username, (char[]) password, false, (String) null);
    }

    public AdministratorToken(String username, String password) {
        this(username, (char[]) (password != null ? password.toCharArray() : null), false, (String) null);
    }

    public AdministratorToken(String username, char[] password, String host) {
        this(username, password, false, host);
    }

    public AdministratorToken(String username, String password, String host) {
        this(username, password != null ? password.toCharArray() : null, false, host);
    }

    public AdministratorToken(String username, char[] password, boolean rememberMe) {
        this(username, (char[]) password, rememberMe, (String) null);
    }

    public AdministratorToken(String username, String password, boolean rememberMe) {
        this(username, (char[]) (password != null ? password.toCharArray() : null), rememberMe, (String) null);
    }

    public AdministratorToken(String username, char[] password, boolean rememberMe, String host) {
        this.rememberMe = false;
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public AdministratorToken(String username, String password, boolean rememberMe, String host) {
        this(username, password != null ? password.toCharArray() : null, rememberMe, host);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return this.password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return this.getUsername();
    }

    @Override
    public Object getCredentials() {
        return this.getPassword();
    }

    @Override
    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean isRememberMe() {
        return this.rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void clear() {
        this.username = null;
        this.host = null;
        this.rememberMe = false;
        if (this.password != null) {
            for (int i = 0; i < this.password.length; ++i) {
                this.password[i] = 0;
            }

            this.password = null;
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append(" - ");
        sb.append(this.username);
        sb.append(", rememberMe=").append(this.rememberMe);
        if (this.host != null) {
            sb.append(" (").append(this.host).append(")");
        }

        return sb.toString();
    }
}
