package net.qqxh.sunflow.server.shiro.oauth2;

import me.zhyd.oauth.request.AuthRequest;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈oauth2服务提供方接口〉<br>
 * 〈使用策略模式提供多种类型的授权来源〉
 *
 * @author jason
 * @fileName: SfAuthRequest.java
 * @date: 2019/5/29 20:30
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface SfAuthRequest {
    String getType();

    AuthRequest getAuthRequest();
}
