package net.qqxh.sunflow.server.shiro.oauth2.request;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthOschinaRequest;
import me.zhyd.oauth.request.AuthRequest;
import net.qqxh.sunflow.server.shiro.oauth2.SfAuthRequest;
import org.springframework.stereotype.Component;

@Component
public class WeChatRequest implements SfAuthRequest {
    @Override
    public String getType() {
        return "weChat";
    }

    @Override
    public AuthRequest getAuthRequest() {
        AuthRequest authRequest = new AuthOschinaRequest(AuthConfig.builder()
                .clientId("3e01eec911bcd667c4907b9e5e421a7124e4ffa4857e71151d5ab1ae8b7895c9")
                .clientSecret("c42a93f52c5ac0fc84d71258e84bf337b5de59448cf284c8e01718e42454545e")
                .redirectUri("http://127.0.0.1/oauth2-login/"+getType())
                .build());
        return authRequest;
    }
}
