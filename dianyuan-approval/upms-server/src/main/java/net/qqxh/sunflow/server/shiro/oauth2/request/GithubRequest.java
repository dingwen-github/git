package net.qqxh.sunflow.server.shiro.oauth2.request;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthRequest;
import net.qqxh.sunflow.server.shiro.oauth2.SfAuthRequest;
import net.qqxh.sunflow.server.shiro.oauth2.authrequest.AuthGithubRequest;
import net.qqxh.sunflow.server.upms.service.ThirdAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GithubRequest implements SfAuthRequest {

    @Autowired
    ThirdAuthUserService thirdAuthUserService;

    @Override
    public String getType() {
        return "github";
    }

    @Override
    public AuthRequest getAuthRequest() {
        AuthRequest authRequest = new AuthGithubRequest(AuthConfig.builder()
                .clientId("de6bd88a2888cc4c89e1")
                .clientSecret("da4297c9219b17c7e354cca7ef7ae40737518de4")
                .redirectUri("http://127.0.0.1/oauth2-login/" + getType())
                .build());
        return authRequest;
    }
}
