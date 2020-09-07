package net.onebean.gateway.service.impl;

import net.onebean.gateway.vo.AppInfoVo;
import net.onebean.gateway.vo.FindAppByAppIdAndSecretReq;
import net.onebean.gateway.enumModel.AuthTypeEnum;
import net.onebean.gateway.common.CacheConstants;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.service.AccessTokenService;
import net.onebean.gateway.service.AppInfoService;
import net.onebean.gateway.service.UagInfoCacheService;
import net.onebean.gateway.vo.GetAccTokenRequest;
import net.onebean.gateway.vo.GetAccTokenVo;
import net.onebean.gateway.vo.GetAccessTokenResponse;
import net.onebean.component.AccessTokenEncoder;
import net.onebean.component.SpringUtil;
import net.onebean.core.error.BusinessException;
import net.onebean.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccessTokenServiceImpl.class);
    private final static String ACCESS_TOKEN_EXPIRE_KEY = "access.token.expire";


    @Autowired
    private UagInfoCacheService cacheService;
    @Autowired
    private AccessTokenEncoder accessTokenEncoder;
    @Autowired
    private AppInfoService appInfoService;


    @Override
    public GetAccessTokenResponse getAccessToken(GetAccTokenRequest getAccTokenRequest) {
        /*参数拆包*/
        String appId = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getAppId).orElse("");
        String secret = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getSecret).orElse("");
        String timestamp = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getTimestamp).orElse("");
        String sign = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getSign).orElse("");
        String tenantId = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getTenantId).orElse("");
        String deviceToken = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getDeviceToken).orElse("");
        String customerId = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getCustomerId).orElse("");
        String accessToken_expire = PropUtil.getInstance().getConfig(ACCESS_TOKEN_EXPIRE_KEY,PropUtil.DEFLAULT_NAME_SPACE);

        /*参数非空校验*/
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(secret) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(sign)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }

        /*时间戳校验异常*/
        if (!TokenCheckUtils.legalTimeStamp4AccToken(timestamp)) {
            LOGGER.info("AccessTokenServiceImpl method getAccessToken currentTimeMillis = " + System.currentTimeMillis());
            throw new BusinessException(ErrorCodesEnum.TIME_STAMP_ERROR.code(), ErrorCodesEnum.TIME_STAMP_ERROR.msg());
        }

        /*签名校验异常*/
        if (!TokenCheckUtils.checkSignMd5(sign, appId, secret, customerId, tenantId, deviceToken, timestamp)) {
            LOGGER.info("AccessTokenServiceImpl method getAccessToken correct sign = " + TokenCheckUtils.sign(appId, secret, customerId, tenantId, deviceToken, timestamp));
            throw new BusinessException(ErrorCodesEnum.SIGN_ERROR.code(), ErrorCodesEnum.SIGN_ERROR.msg());
        }


        if (StringUtils.isEmpty(accessToken_expire)) {
            throw new BusinessException(ErrorCodesEnum.READ_VALUE_FROM_APOLLO.code(), ErrorCodesEnum.READ_VALUE_FROM_APOLLO.msg() + " field of accessToken_expire");
        }

        /*通过spring cloud接口校验入参的合法性*/
        FindAppByAppIdAndSecretReq req = new FindAppByAppIdAndSecretReq();
        req.setOpenId(appId);
        req.setSecret(secret);
        AppInfoVo app = appInfoService.findByAppIdAndSecret(req);
        if (null == app || StringUtils.isEmpty(app.toString())) {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg() + " appId || secret is not exits");
        }

        String authType = Optional.of(app).map(AppInfoVo::getAuthType).orElse(null);

        if (StringUtils.isEmpty(authType)) {
            throw new BusinessException(ErrorCodesEnum.GET_DATE_ERR.code(), ErrorCodesEnum.GET_DATE_ERR.msg() + " method of findByAppIdAndSecret,authType is empty");
        }

        /*装在缓存信息*/
        GetAccTokenVo vo = new GetAccTokenVo();
        vo.setAccessTokenExpire(accessToken_expire);
        /*生成AccessToken*/
        String accessToken = accessTokenEncoder.generatorAccessToken(appId, secret, timestamp);
        String accessTokenCacheKey = CacheConstants.generateKey(CacheConstants.UagScopeKeys.ACCESSTOKEN_KEY.getValue(), appId);
        try {
            BeanUtils.copyProperties(vo, getAccTokenRequest);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg() + " appId || secret is not exits");
        }
        vo.setAccessToken(accessToken);

        /*私有令牌模式*/
        if (authType.equals(AuthTypeEnum.OAUTH_PRIVATE_CODE.getKey()) || authType.equals(AuthTypeEnum.AVOID_LOGIN.getKey())) {

            /*如果deviceToken为空,说明私有令牌第一次请求,生成deviceToken*/
            if (StringUtils.isEmpty(deviceToken)) {
                deviceToken = this.generateDeviceToken(appId);
                vo.setDeviceToken(deviceToken);
            }


            if (authType.equals(AuthTypeEnum.AVOID_LOGIN.getKey())) {
                if (StringUtils.isEmpty(customerId)) {
                    throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " field of customerId");
                }
            }

            accessTokenCacheKey = CacheConstants.generateKey(accessTokenCacheKey, CacheConstants.UagScopeKeys.PRIVATE_TOKEN_KEY.getValue());
            accessTokenCacheKey = CacheConstants.generateKey(accessTokenCacheKey, accessToken);

            vo.setAccessTokenCacheKey(accessTokenCacheKey);
            /*设置私有令牌缓存*/
            Boolean flag = cacheService.cacheAccessToken(vo);
            if (!flag) {
                throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(), ErrorCodesEnum.PUT_CACHE_ERR.msg() + " cache accessToken failure");
            }
            return new GetAccessTokenResponse(appId, accessToken, deviceToken, accessToken_expire);
        }


        /*设置redis缓存*/
        vo.setAccessTokenCacheKey(accessTokenCacheKey);
        cacheService.cacheAccessToken(vo);
        return new GetAccessTokenResponse(appId, accessToken, deviceToken, accessToken_expire);
    }

    @Override
    public String generateDeviceToken(String appId) {
        String deviceToken;
        String platformCode = WebUtils.getPlatform(SpringUtil.getHttpServletRequest());
        deviceToken = platformCode + EncryptionUtils.sha1(platformCode + System.currentTimeMillis() + appId);
        return platformCode+deviceToken+appId;
    }
}
