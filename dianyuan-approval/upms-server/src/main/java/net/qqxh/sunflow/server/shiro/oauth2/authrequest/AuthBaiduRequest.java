package net.qqxh.sunflow.server.shiro.oauth2.authrequest;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthBaiduErrorCode;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthSource;
import me.zhyd.oauth.request.ResponseStatus;
import me.zhyd.oauth.utils.UrlBuilder;
import net.qqxh.sunflow.server.upms.bean.ThirdAuthUser;

public class AuthBaiduRequest extends BaseAuthRequest {
    public AuthBaiduRequest(AuthConfig config) {
        super(config, AuthSource.BAIDU);
    }

    @Override
    protected String getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getBaiduAccessTokenUrl(this.config.getClientId(), this.config.getClientSecret(), code, this.config.getRedirectUri());
        HttpResponse response = HttpRequest.post(accessTokenUrl).execute();
        JSONObject accessTokenObject = JSONObject.parseObject(response.body());
        AuthBaiduErrorCode errorCode = AuthBaiduErrorCode.getErrorCode(accessTokenObject.getString("error"));
        if (!AuthBaiduErrorCode.OK.equals(errorCode)) {
            throw new AuthException(errorCode.getDesc());
        } else {
            return accessTokenObject.getString("access_token");
        }
    }

    @Override
    protected ThirdAuthUser getUserInfo(String accessToken) {
        HttpResponse response = HttpRequest.get(UrlBuilder.getBaiduUserInfoUrl(accessToken)).execute();
        String userInfo = response.body();
        JSONObject object = JSONObject.parseObject(userInfo);
        AuthBaiduErrorCode errorCode = AuthBaiduErrorCode.getErrorCode(object.getString("error"));
        if (!AuthBaiduErrorCode.OK.equals(errorCode)) {
            throw new AuthException(errorCode.getDesc());
        } else {
            ThirdAuthUser thirdAuthUser=new ThirdAuthUser();
            thirdAuthUser.setUserId(object.getString("userid"));
            thirdAuthUser.setUsername(object.getString("username"));
            thirdAuthUser.setNickname(object.getString("username"));
            thirdAuthUser.setGender(object.getString("sex"));
            thirdAuthUser.setAvatar("http://tb.himg.baidu.com/sys/portraitn/item/"+object.getString("portrait"));
            thirdAuthUser.setSource(AuthSource.BAIDU.name());
            return thirdAuthUser;
        }
    }

    /**
     * 取消授权
     * @param accessToken
     * @return
     */
    @Override
    public AuthResponse revoke(String accessToken) {
        HttpResponse response = HttpRequest.get(UrlBuilder.getBaiduRevokeUrl(accessToken)).execute();
        String userInfo = response.body();
        JSONObject object = JSONObject.parseObject(userInfo);
        ResponseStatus status = object.getIntValue("result") == 1 ? ResponseStatus.SUCCESS : ResponseStatus.FAILURE;
        return AuthResponse.builder().code(status.getCode()).msg(status.getMsg()).build();
    }
}
