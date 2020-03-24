package net.qqxh.sunflow.server.shiro.oauth2.authrequest;

import lombok.Data;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthSource;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.ResponseStatus;
import me.zhyd.oauth.utils.AuthConfigChecker;
import me.zhyd.oauth.utils.UrlBuilder;
import net.qqxh.sunflow.server.upms.bean.ThirdAuthUser;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
@Data
public abstract class BaseAuthRequest implements AuthRequest {
    protected AuthConfig config;
    protected AuthSource source;

    public BaseAuthRequest(AuthConfig config, AuthSource source) {
        this.config = config;
        this.source = source;
        if (!AuthConfigChecker.isSupportedAuth(config)) {
            throw new AuthException(ResponseStatus.PARAMETER_INCOMPLETE);
        }
    }

    protected abstract String getAccessToken(String code);

    protected abstract ThirdAuthUser getUserInfo(String accessToken);

    @Override
    public AuthResponse login(String code) {
        try {
            ThirdAuthUser user = this.getUserInfo(this.getAccessToken(code));
            return AuthResponse.builder().data(user).build();
        } catch (Exception e) {
            return AuthResponse.builder().code(500).msg(e.getMessage()).build();
        }
    }

    @Override
    public String authorize() {
        String authorizeUrl = null;
        switch (source) {
            case WEIBO:
                authorizeUrl = UrlBuilder.getWeiboAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case BAIDU:
                authorizeUrl = UrlBuilder.getBaiduAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case DINGTALK:
                authorizeUrl = UrlBuilder.getDingTalkQrConnectUrl(config.getClientId(), config.getRedirectUri());
                break;
            case GITEE:
                authorizeUrl = UrlBuilder.getGiteeAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case GITHUB:
                authorizeUrl = UrlBuilder.getGithubAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case CSDN:
                authorizeUrl = UrlBuilder.getCsdnAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case CODING:
                authorizeUrl = UrlBuilder.getCodingAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case TENCEN_CLOUD:
                authorizeUrl = UrlBuilder.getTencentCloudAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case OSCHINA:
                authorizeUrl = UrlBuilder.getOschinaAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case ALIPAY:
                authorizeUrl = UrlBuilder.getAlipayAuthorizeUrl(config.getClientId(), config.getRedirectUri());
                break;
            case QQ:
                break;
            case WECHAT:
                break;
            case GOOGLE:
                break;
            default:
                break;
        }
        return authorizeUrl;
    }
}
