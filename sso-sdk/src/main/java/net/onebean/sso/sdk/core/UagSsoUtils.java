package net.onebean.sso.sdk.core;

import net.onebean.component.SpringUtil;
import net.onebean.core.form.Parse;
import net.onebean.core.model.UagLoginSessionInfo;
import net.onebean.util.EncodingUtils;
import net.onebean.util.JSONUtil;
import net.onebean.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * 单点登录工具类
 * @author 0neBean
 */
public class UagSsoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UagSsoUtils.class);
    private static final String UAG_SSO_LOGIN_INFO = "UAG-SSO-LOGIN-INFO";

    /**
     * 获取单点登录session中用户信息
     * @return UagLoginSessionInfo
     */
    public static UagLoginSessionInfo getCurrentUagLoginSessionInfo() {
        UagLoginSessionInfo uagLoginInfo;
        try {
            String uagLoginInfoJson = Optional.ofNullable(SpringUtil.getHttpServletRequest().getSession().getAttribute(UAG_SSO_LOGIN_INFO)).map(s -> s + "").orElse("");
            uagLoginInfo = JSONUtil.toBean(EncodingUtils.decoderBase64(uagLoginInfoJson), UagLoginSessionInfo.class);
            LOGGER.info("getCurrentUagLoginInfo uagLoginInfo = " + uagLoginInfo);
        } catch (Exception e) {
            LOGGER.error("getCurrentUagLoginSessionInfo failure e = ", e);
            return null;
        }
        return uagLoginInfo;
    }

    /**
     * 设置当前用户登录信息到Session
     * @param uagLoginInfo 用户登录信
     */
    public static void setCurrentUagLoginSessionInfo(UagLoginSessionInfo uagLoginInfo) {
        try {
            HttpSession session = SpringUtil.getHttpServletRequest().getSession();
            session.setAttribute(UAG_SSO_LOGIN_INFO, EncodingUtils.encoderBase64(JSONUtil.toJson(uagLoginInfo)));
        } catch (Exception e) {
            LOGGER.error("getCurrentUagLoginSessionInfo failure e = ", e);
        }
    }

    /**
     * 单点登录登出操作
     * @return bool
     */
    public static Boolean ssoLogOut(){
        return SsoService.getInstance().logout();
    }


    /**
     * 根据session中登录信息，设置对象所属信息
     * @param model 对象
     */
    public static void setUagUserInfoBySession(Object model){
        UagLoginSessionInfo uagLoginInfo = getCurrentUagLoginSessionInfo();
        String uagUserId = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagUserId).orElse("");
        String uagUserNickName = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagUserNickName).orElse("");
        try {
            ReflectionUtils.setFieldValue(model,"operatorId", Parse.toInt(uagUserId));
            ReflectionUtils.setFieldValue(model,"operatorName",uagUserNickName);
        } catch (Exception e) {
            LOGGER.error("setUagUserInfoBySession failure e = ", e);
        }
    }

}
