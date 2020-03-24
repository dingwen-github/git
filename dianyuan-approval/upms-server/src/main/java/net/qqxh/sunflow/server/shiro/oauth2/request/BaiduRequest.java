package net.qqxh.sunflow.server.shiro.oauth2.request;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthRequest;
import net.qqxh.sunflow.server.shiro.oauth2.SfAuthRequest;
import net.qqxh.sunflow.server.shiro.oauth2.authrequest.AuthBaiduRequest;
import net.qqxh.sunflow.server.upms.service.ThirdAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaiduRequest implements SfAuthRequest {

    @Autowired
    ThirdAuthUserService thirdAuthUserService;
    @Override
    public String getType() {
        return "typebd";
    }

    @Override
    public AuthRequest getAuthRequest() {
        AuthRequest authRequest = new AuthBaiduRequest(AuthConfig.builder()
                .clientId("88VXLxVv1Gjuj0MsO3i5bSUn")
                .clientSecret("xS2GWUXrZPSuYWoTyukLl5rkHiYkjQj7")
                .redirectUri("http://127.0.0.1/oauth2-login/" + getType())
                .build());
        return authRequest;
    }
}