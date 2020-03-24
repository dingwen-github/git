package net.qqxh.sunflow.server.shiro.oauth2.authrequest;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthSource;
import me.zhyd.oauth.model.AuthUserGender;
import me.zhyd.oauth.utils.UrlBuilder;
import net.qqxh.sunflow.server.upms.bean.ThirdAuthUser;

public class AuthOschinaRequest extends BaseAuthRequest {

    public AuthOschinaRequest(AuthConfig config) {
        super(config, AuthSource.OSCHINA);
    }

    @Override
    protected String getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getOschinaAccessTokenUrl(config.getClientId(), config.getClientSecret(), code, config.getRedirectUri());
        HttpResponse response = HttpRequest.post(accessTokenUrl).execute();
        JSONObject accessTokenObject = JSONObject.parseObject(response.body());
        if (accessTokenObject.containsKey("error")) {
            throw new AuthException("Unable to get token from oschina using code [" + code + "]");
        }
        return accessTokenObject.getString("access_token");
    }

    @Override
    protected ThirdAuthUser getUserInfo(String accessToken) {
        HttpResponse response = HttpRequest.get(UrlBuilder.getOschinaUserInfoUrl(accessToken)).execute();
        JSONObject object = JSONObject.parseObject(response.body());
        if (object.containsKey("error")) {
            throw new AuthException(object.getString("error_description"));
        }
        ThirdAuthUser thirdAuthUser=new ThirdAuthUser();
        thirdAuthUser.setUserId(object.getString("id"));
        thirdAuthUser.setUsername(object.getString("name"));
        thirdAuthUser.setNickname(object.getString("name"));
        thirdAuthUser.setGender(String.valueOf(AuthUserGender.getRealGender(object.getString("gender")).getCode()));
        thirdAuthUser.setBlog(object.getString("url"));
        thirdAuthUser.setLocation(object.getString("location"));
        thirdAuthUser.setEmail(object.getString("email"));
        thirdAuthUser.setAvatar(object.getString("avatar"));
        thirdAuthUser.setAccessToken(accessToken);
        thirdAuthUser.setSource(AuthSource.OSCHINA.name());
        return thirdAuthUser;

    }
}
