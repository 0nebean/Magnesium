package net.onebean.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import net.onebean.component.redis.IRedisService;
import net.onebean.core.error.BusinessException;
import net.onebean.core.form.Parse;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.service.UagInfoCacheService;
import net.onebean.gateway.vo.SendLoginSmsReq;
import net.onebean.gateway.common.CacheConstants;
import net.onebean.gateway.vo.GetAccTokenVo;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UagInfoCacheServiceImpl implements UagInfoCacheService {

    @Autowired
    private IRedisService iRedisService;

    private final static int UAG_SMS_SEND_PHONE_NUMBER_COUNT_AN_HOUR_LIMIT = 3;
    private final static int UAG_SMS_SEND_IP_COUNT_DAY_LIMIT = 5;
    private final static long UAG_SMS_SEND_PHONE_NUMBER_COUNT_AN_HOUR_LIMIT_TIME_ZONE = 60*60;
    private final static long UAG_SMS_SEND_IP_COUNT_DAY_LIMIT_TIME_ZONE = 60*60*24;

    @Override
    public Boolean cacheSmsInfo(SendLoginSmsReq req) {
        long timeOut = Optional.ofNullable(req).map(SendLoginSmsReq::getTimeOut).orElse(0L);
        String telPhone = Optional.ofNullable(req).map(SendLoginSmsReq::getTelPhone).orElse(null);
        String smsCode = Optional.ofNullable(req).map(SendLoginSmsReq::getSmsCode).orElse(null);
        String deviceToken = Optional.ofNullable(req).map(SendLoginSmsReq::getDeviceToken).orElse(null);
        String redisKey = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_OPENAPI_LOGIN_SMS_CODE.getValue(),telPhone);
        if (StringUtils.isEmpty(telPhone)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of telPhone is empty");
        }
        if (StringUtils.isEmpty(smsCode)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of smsCode is empty");
        }
        if (StringUtils.isEmpty(deviceToken)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of deviceToken is empty");
        }
        if (timeOut <= 0){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of timeOut is empty");
        }
        try {
            iRedisService.set(redisKey, JSON.toJSONString(req),timeOut);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(),ErrorCodesEnum.PUT_CACHE_ERR.msg());
        }
        return true;
    }

    public void countIpAddressDay(String ipAddress){
        String key = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_SMS_SEND_IP_COUNT_DAY.getValue(),ipAddress);
        int count = Optional.ofNullable(iRedisService.get(key)).map(Parse::toInt).orElse(0);
        count = count+1;
        iRedisService.set(key, Integer.toString(count), UAG_SMS_SEND_IP_COUNT_DAY_LIMIT_TIME_ZONE);
    }

    public Boolean checkIpAddressDay(String ipAddress){
        String key = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_SMS_SEND_IP_COUNT_DAY.getValue(),ipAddress);
        Integer count = Optional.ofNullable(iRedisService.get(key)).map(Parse::toInt).orElse(0);
        return count < UAG_SMS_SEND_IP_COUNT_DAY_LIMIT;
    }

    public void countSmsAnHour(String telPhone){
        String key = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_SMS_SEND_COUNT_PHONE_NUMBER_AN_HOUR.getValue(),telPhone);
        int count = Optional.ofNullable(iRedisService.get(key)).map(Parse::toInt).orElse(0);
        count = count+1;
        iRedisService.set(key, Integer.toString(count), UAG_SMS_SEND_PHONE_NUMBER_COUNT_AN_HOUR_LIMIT_TIME_ZONE);
    }

    public Boolean checkSmsAnHour(String telPhone){
        String key = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_SMS_SEND_COUNT_PHONE_NUMBER_AN_HOUR.getValue(),telPhone);
        Integer count = Optional.ofNullable(iRedisService.get(key)).map(Parse::toInt).orElse(0);
        return count < UAG_SMS_SEND_PHONE_NUMBER_COUNT_AN_HOUR_LIMIT;
    }

    @Override
    public Boolean cacheAccessToken(GetAccTokenVo getAccTokenVo) {
        String appId = Optional.of(getAccTokenVo).map(GetAccTokenVo::getAppId).orElse("");
        String accessToken_expire = Optional.of(getAccTokenVo).map(GetAccTokenVo::getAccessTokenExpire).orElse("");
        String accessTokenCacheKey = Optional.of(getAccTokenVo).map(GetAccTokenVo::getAccessTokenCacheKey).orElse("");
        if (StringUtils.isEmpty(appId)) {
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(), ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg() + " appId is empty");
        }
        if (StringUtils.isEmpty(accessToken_expire)) {
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(), ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg() + " accessToken_expire is empty");
        }
        if (StringUtils.isEmpty(accessTokenCacheKey)) {
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(), ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg() + " accessTokenCacheKey is empty");
        }
        try {
            iRedisService.set(accessTokenCacheKey, JSON.toJSONString(getAccTokenVo), Parse.toLong(accessToken_expire));
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(),ErrorCodesEnum.PUT_CACHE_ERR.msg());
        }
        return true;
    }
}
