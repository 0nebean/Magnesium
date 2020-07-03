package net.onebean.sso.sdk.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import net.onebean.component.SpringUtil;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.model.UagLoginSessionInfo;
import net.onebean.sso.sdk.common.ConstantEnum;
import net.onebean.sso.sdk.vo.CheckUagLoginStatusReq;
import net.onebean.sso.sdk.vo.GetAccTokenRequest;
import net.onebean.sso.sdk.vo.GetAccessTokenResponse;
import net.onebean.sso.sdk.vo.UagLoginInfo;
import net.onebean.sso.sdk.vo.enumModel.PrivateTokenLoginFlagEnum;
import net.onebean.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import sun.misc.Unsafe;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 登出操作
 * @author 0neBean
 */
final class SsoService {

    private SsoService() {
    }

    /**
     * 当前对象实例
     */
    private static final SsoService ssoService = new SsoService();
    /**
     * 获取当前对象实例
     * @return ssoService
     */
    public static SsoService getInstance() {
        return ssoService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoService.class);

    private static final String SSO_BASE_OAUTH_URL;
    private static final String SSO_UAG_APP_ID;
    private static final String SSO_UAG_SECRET;
    private static final String SSO_UAG_ACCESS_TOKEN_URL;
    private static final String SSO_UAG_ACCESS_SCHEME;
    private static final String UAG_LOG_OUT_URL;
    private static final String UAG_CHECK_LOGIN_STATUS_URL;

    static {
        String SSO_UAG_ACCESS_HOST = PropUtil.getInstance().getConfig("sso.uag.access.host", PropUtil.PUBLIC_CONF_SSO);
        SSO_UAG_APP_ID = PropUtil.getInstance().getConfig("sso.uag.app.id", PropUtil.PUBLIC_CONF_SSO);
        SSO_UAG_SECRET = PropUtil.getInstance().getConfig("sso.uag.secret", PropUtil.PUBLIC_CONF_SSO);
        SSO_UAG_ACCESS_SCHEME = PropUtil.getInstance().getConfig("sso.uag.access.scheme", PropUtil.PUBLIC_CONF_SSO);
        SSO_BASE_OAUTH_URL = SSO_UAG_ACCESS_SCHEME + "://" + SSO_UAG_ACCESS_HOST + "/sso/index.html";
        SSO_UAG_ACCESS_TOKEN_URL = SSO_UAG_ACCESS_SCHEME + "://" + SSO_UAG_ACCESS_HOST + "/auth/getAccessToken";
        UAG_LOG_OUT_URL = SSO_UAG_ACCESS_SCHEME + "://" + SSO_UAG_ACCESS_HOST + "/auth/uagSsoLogOut";
        UAG_CHECK_LOGIN_STATUS_URL = SSO_UAG_ACCESS_SCHEME + "://" + SSO_UAG_ACCESS_HOST + "/auth/checkUagLoginInfo";
    }

    /**
     * 远程校验用户登录信息
     *
     * @param appId       应用ID
     * @param accessToken 令牌
     * @param deviceToken 设备码
     * @return UagLoginInfo
     */
    private static UagLoginInfo checkUagLoginInfo(String appId, String accessToken, String deviceToken) {
        CheckUagLoginStatusReq request = new CheckUagLoginStatusReq();
        request.setAppId(appId);
        request.setDeviceToken(deviceToken);
        String respJsonStr = (String) RestUtils.getUagRequestInstance(appId, accessToken).doPostForObj(UAG_CHECK_LOGIN_STATUS_URL, request, String.class);
        BaseResponse<UagLoginInfo> resp = JSON.parseObject(respJsonStr, new TypeReference<BaseResponse<UagLoginInfo>>() {
        });
        UagLoginInfo uagLoginInfo = Optional.ofNullable(resp).map(BaseResponse::getData).orElse(new UagLoginInfo());
        String errMessage = Optional.ofNullable(resp).map(BaseResponse::getErrMsg).orElse("");
        LOGGER.debug("checkUagLoginInfo ,uagLoginInfo = " + uagLoginInfo);
        Boolean isLogin = getIsLoginFromRemoteUagLoginInfo(uagLoginInfo);
        if (!isLogin){
            LOGGER.error("checkUagLoginInfo ,get error resp,msg = " + errMessage);
        }
        return uagLoginInfo;
    }

    /**
     * 获取用户登录状态
     *
     * @param uagLoginInfo 用户登录信息
     * @return Boolean
     */
    private static Boolean getIsLoginFromRemoteUagLoginInfo(UagLoginInfo uagLoginInfo) {
        String isLogin = Optional.ofNullable(uagLoginInfo).map(UagLoginInfo::getLoginStatus).orElse("");
        return isLogin.equals(PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_LOGIN.getKey());
    }

    /**
     * 设置登录信息到session
     *
     * @param uagLoginInfo 用户登录信息
     * @param appId        应用ID
     * @param accessToken  令牌
     * @param deviceToken  设备码
     */
    private static void setLoginInfoSession(UagLoginInfo uagLoginInfo, String appId, String accessToken, String deviceToken) {
        UagLoginSessionInfo info = new UagLoginSessionInfo();
        BeanUtils.copyProperties(uagLoginInfo, info);
        info.setAccessToken(accessToken);
        info.setUagAppId(appId);
        info.setUagDeviceToken(deviceToken);
        UagSsoUtils.setCurrentUagLoginSessionInfo(info);
    }

    /**
     * 设置登录信息到session
     *
     * @param appId       应用ID
     * @param accessToken 令牌
     * @param deviceToken 设备码
     */
    private static void setLoginInfoSession(String appId, String accessToken, String deviceToken) {
        UagLoginSessionInfo info = new UagLoginSessionInfo();
        info.setUagAppId(appId);
        info.setAccessToken(accessToken);
        info.setUagDeviceToken(deviceToken);
        UagSsoUtils.setCurrentUagLoginSessionInfo(info);
    }

    /**
     * 获取accessToken
     * @return accessToken obj
     */
    private static GetAccessTokenResponse getAccessToken() {
        GetAccTokenRequest request = new GetAccTokenRequest();
        String timestamp = DateUtils.getTimeStamp();
        request.setAppId(SSO_UAG_APP_ID);
        request.setSecret(SSO_UAG_SECRET);
        request.setTimestamp(timestamp);
        request.setSign(TokenCheckUtils.sign(SSO_UAG_APP_ID, SSO_UAG_SECRET, timestamp));
        String respJsonStr = (String) RestUtils.getInstance().doPostForObj(SSO_UAG_ACCESS_TOKEN_URL, request, String.class);
        BaseResponse<GetAccessTokenResponse> resp = JSON.parseObject(respJsonStr, new TypeReference<BaseResponse<GetAccessTokenResponse>>() {
        });
        return Optional.ofNullable(resp).map(BaseResponse::getData).orElse(null);
    }

    /**
     * 获取单点登录RUL
     *
     * @param accessToken 令牌
     * @param deviceToken 设备码
     * @return oauth url
     */
    private static String generateSsoCompleteUrl(String accessToken, String deviceToken) {
        String oauthUrl = SSO_BASE_OAUTH_URL + "#/" + "?uagAppId=" + SSO_UAG_APP_ID + "&uagAccessToken=" + accessToken + "&uagDeviceToken=" + deviceToken;
        LOGGER.error("oauthUrl is = " + oauthUrl);
        //设置当前的信息到session
        setLoginInfoSession(SSO_UAG_APP_ID, accessToken, deviceToken);
        return oauthUrl;
    }

    /**
     * 获取单点登录RUL
     *
     * @return oauth url
     */
    public String generateSsoCompleteUrl() {
        GetAccessTokenResponse resp = getAccessToken();
        String accessToken = Optional.ofNullable(resp).map(GetAccessTokenResponse::getAccessToken).orElse("");
        String deviceToken = Optional.ofNullable(resp).map(GetAccessTokenResponse::getDeviceToken).orElse("");
        String oauthUrl = SSO_BASE_OAUTH_URL + "#/" + "?uagAppId=" + SSO_UAG_APP_ID + "&uagAccessToken=" + accessToken + "&uagDeviceToken=" + deviceToken;
        LOGGER.error("oauthUrl is = " + oauthUrl);
        //设置当前的信息到session
        setLoginInfoSession(SSO_UAG_APP_ID, accessToken, deviceToken);
        return oauthUrl;
    }

    /**
     * 检查用户登录状态
     * @param request  req
     * @return res uri
     */
    public String checkLoginStatus(HttpServletRequest request){
        /*获取参数里的deviceToken*/
        String deviceTokenFromReqParam = Optional.ofNullable(request).map(r -> r.getParameter(ConstantEnum.UAG_DEVICE_TOKEN_PARAM_KEY.getName())).orElse("");
        String accessToken = Optional.ofNullable(UagSsoUtils.getCurrentUagLoginSessionInfo()).map(UagLoginSessionInfo::getAccessToken).orElse("");
        String deviceToken = Optional.ofNullable(UagSsoUtils.getCurrentUagLoginSessionInfo()).map(UagLoginSessionInfo::getUagDeviceToken).orElse("");
        String finalSsoUrl;

        if (StringUtils.isEmpty(deviceTokenFromReqParam) && StringUtils.isEmpty(deviceToken)) {
            /*未登录*/
            GetAccessTokenResponse resp = getAccessToken();
            accessToken = Optional.ofNullable(resp).map(GetAccessTokenResponse::getAccessToken).orElse("");
            deviceToken = Optional.ofNullable(resp).map(GetAccessTokenResponse::getDeviceToken).orElse("");
            finalSsoUrl = generateSsoCompleteUrl(accessToken, deviceToken);
            LOGGER.info("will sendRedirect to = " + finalSsoUrl);
            return finalSsoUrl;
        } else if (StringUtils.isNotEmpty(deviceTokenFromReqParam) || StringUtils.isNotEmpty(deviceToken)) {
            /*登录成功后第一次请求*/
            UagLoginInfo loginInfo = checkUagLoginInfo(SSO_UAG_APP_ID, accessToken, deviceToken);
            Boolean isLogin = getIsLoginFromRemoteUagLoginInfo(loginInfo);
            if (isLogin) {
                setLoginInfoSession(loginInfo, SSO_UAG_APP_ID, accessToken, deviceToken);
                if (StringUtils.isNotEmpty(deviceTokenFromReqParam)){
                    return Optional.ofNullable(request).map(HttpServletRequest::getRequestURI).orElse("");
                }
            } else {
                finalSsoUrl = generateSsoCompleteUrl();
                LOGGER.info("will sendRedirect to = " + finalSsoUrl);
                /*如果当前登录失效 重定向到登录页面*/
                return finalSsoUrl;
            }
        }
        return "";
    }

    /**
     * 用户登出操作
     *
     * @return bool
     */
    public Boolean logout() {
        try {
            UagLoginSessionInfo uagLoginInfo = UagSsoUtils.getCurrentUagLoginSessionInfo();
            String uagDeviceToken = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagDeviceToken).orElse("");
            String appId = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagAppId).orElse("");
            String uagUsername = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagUsername).orElse("");
            String accessToken = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getAccessToken).orElse("");
            if (StringUtils.isEmpty(uagDeviceToken) || StringUtils.isEmpty(appId) || StringUtils.isEmpty(accessToken)) {
                return false;
            }
            SpringUtil.getHttpServletRequest().getSession().removeAttribute(ConstantEnum.UAG_SSO_LOGIN_INFO.getName());
            LOGGER.info("did logout ,deviceToken = " + uagDeviceToken + " uagAppId = " + appId + " uagUsername = " + uagUsername);

            CheckUagLoginStatusReq request = new CheckUagLoginStatusReq();
            request.setDeviceToken(uagDeviceToken);
            request.setAppId(appId);

            String respJsonStr = (String) RestUtils.getUagRequestInstance(appId, accessToken).doPostForObj(UAG_LOG_OUT_URL, request, String.class);
            BaseResponse<Boolean> resp = JSON.parseObject(respJsonStr, new TypeReference<BaseResponse<Boolean>>() {
            });

            Boolean deleteLoginFlag = Optional.ofNullable(resp).map(BaseResponse::getData).orElse(false);
            LOGGER.info("did logout ,deleteLoginFlag = " + deleteLoginFlag);
            return deleteLoginFlag;
        } catch (Exception e) {
            LOGGER.error("logout failure e = ", e);
            return false;
        }
    }
}
