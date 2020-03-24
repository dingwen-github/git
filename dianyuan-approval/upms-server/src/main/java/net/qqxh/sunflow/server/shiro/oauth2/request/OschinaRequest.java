package net.qqxh.sunflow.server.shiro.oauth2.request;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthRequest;
import net.qqxh.sunflow.server.shiro.oauth2.SfAuthRequest;
import net.qqxh.sunflow.server.shiro.oauth2.authrequest.AuthOschinaRequest;
import org.springframework.stereotype.Component;

@Component
public class OschinaRequest implements SfAuthRequest {
    @Override
    public String getType() {
        return "oschina";
    }

    @Override
    public AuthRequest getAuthRequest() {
        AuthRequest authRequest = new AuthOschinaRequest(AuthConfig.builder()
                .clientId("u8jd4wew8Lw14C3hKwKJ")
                .clientSecret("ckMBplKaXYwW6392XYZvKafdST9lSENR")
                .redirectUri("http://127.0.0.1/oauth2-login/"+getType())
                .build());
        return authRequest;
    }
}