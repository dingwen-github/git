package net.qqxh.sunflow.server.shiro.config;

import io.buji.pac4j.context.ShiroSessionStore;
import net.qqxh.sunflow.server.shiro.service.AbstractShiroService;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈pcak4j单点登录配置类〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: Pac4jConfig.java
 * @date: 2019/5/29 20:20
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
@ConditionalOnExpression("'${cas.enable}'.equals('true')")
public class Pac4jConfig {
    @Autowired
    AbstractShiroService shiroService;
    /**
     * 地址为：cas地址
     */
    @Value("${cas.server.url}")
    private String casServerUrl;

    /**
     * 地址为：验证返回后的项目地址：http://localhost:8081
     */
    @Value("${cas.project.url}")
    private String projectUrl;

    /**
     * 相当于一个标志，可以随意
     */
    @Value("${cas.client-name}")
    private String clientName;


    /**
     * pac4j配置
     *
     * @param
     * @param
     * @return
     */
    @Bean("authcConfig")
    public Config config() {
        Config config = new Config(casClient());
        config.setSessionStore(shiroSessionStore());

        return config;
    }

    /**
     * 自定义存储
     *
     * @return
     */
    @Bean
    public ShiroSessionStore shiroSessionStore() {
        return new ShiroSessionStore();
    }

    /**
     * cas 客户端配置
     *
     * @param
     * @return
     */
    @Bean
    public CasClient casClient() {
        CasClient casClient = new CasClient(casConfig());
        //客户端回调地址
        casClient.setCallbackUrl(projectUrl + "/callback?client_name=" + clientName);
        casClient.setName(clientName);
        return casClient;
    }

    /**
     * 请求cas服务端配置
     *
     * @param
     */
    @Bean
    public CasConfiguration casConfig() {
        final CasConfiguration configuration = new CasConfiguration();
        //CAS server登录地址
        configuration.setLoginUrl(casServerUrl + "/login");
        //CAS 版本，默认为 CAS30，我们使用的是 CAS20
        configuration.setProtocol(CasProtocol.CAS20);
        configuration.setAcceptAnyProxy(true);
        configuration.setPrefixUrl(casServerUrl + "/");
        configuration.addCustomParam("", "");
        return configuration;
    }


}
