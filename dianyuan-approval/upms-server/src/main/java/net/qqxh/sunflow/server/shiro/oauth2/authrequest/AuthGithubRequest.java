package net.qqxh.sunflow.server.shiro.oauth2.authrequest;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthSource;
import me.zhyd.oauth.utils.GlobalAuthUtil;
import me.zhyd.oauth.utils.UrlBuilder;
import net.qqxh.sunflow.server.upms.bean.ThirdAuthUser;

import java.util.Map;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class AuthGithubRequest extends BaseAuthRequest {

    public AuthGithubRequest(AuthConfig config) {
        super(config, AuthSource.GITHUB);
    }

    @Override
    protected String getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getGithubAccessTokenUrl(config.getClientId(), config.getClientSecret(), code, config.getRedirectUri());
        HttpResponse response = HttpRequest.post(accessTokenUrl).execute();
        Map<String, String> res = GlobalAuthUtil.parseStringToMap(response.body());
        if (res.containsKey("error")) {
            throw new AuthException(res.get("error") + ":" + res.get("error_description"));
        }
        return res.get("access_token");
    }

    @Override
    protected ThirdAuthUser getUserInfo(String accessToken) {
        HttpResponse response = HttpRequest.get(UrlBuilder.getGithubUserInfoUrl(accessToken)).execute();
        String userInfo = response.body();
        JSONObject object = JSONObject.parseObject(userInfo);
        ThirdAuthUser thirdAuthUser=new ThirdAuthUser();
        thirdAuthUser.setUserId(object.getString("id"));
        thirdAuthUser.setUsername(object.getString("login"));
        thirdAuthUser.setBlog(object.getString("blog"));
        thirdAuthUser.setNickname(object.getString("name"));
        thirdAuthUser.setCompany(object.getString("company"));
        thirdAuthUser.setGender(object.getString("sex"));
        thirdAuthUser.setAvatar(object.getString("avatar_url"));
        thirdAuthUser.setLocation(object.getString("location"));
        thirdAuthUser.setEmail(object.getString("email"));
        thirdAuthUser.setRemark(object.getString("bio"));
        thirdAuthUser.setAccessToken(accessToken);
        thirdAuthUser.setSource(AuthSource.GITHUB.name());
        return thirdAuthUser;
    }
}
