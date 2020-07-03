package net.onebean.user.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import net.onebean.component.redis.IRedisService;
import net.onebean.core.error.BusinessException;
import net.onebean.user.mngt.common.CacheConstants;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.service.UagInfoCacheService;
import net.onebean.user.mngt.vo.SendLoginSmsReq;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UagInfoCacheServiceImpl implements UagInfoCacheService {

    @Autowired
    private IRedisService iRedisService;

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
}
