package net.qqxh.sunflow.server.shiro.oauth2;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈oauth2授权服务提供工厂〉<br>
 * 〈根据认证类型获取某种类型oauth2服务方进行授权〉
 *
 * @author jason
 * @fileName: AuthRequestFactory.java
 * @date: 2019/5/29 20:29
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
public class AuthRequestFactory {
    @Autowired
    private List<SfAuthRequest> authRequest;

    public SfAuthRequest getAuthRequest(String type) {
        for (SfAuthRequest authRequest : authRequest) {
            if (StringUtils.equalsIgnoreCase(type, authRequest.getType())) {
                return authRequest;
            }
        }
        return null;
    }

}
