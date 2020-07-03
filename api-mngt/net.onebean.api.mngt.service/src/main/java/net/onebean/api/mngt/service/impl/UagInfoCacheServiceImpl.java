package net.onebean.api.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import net.onebean.api.mngt.common.ErrorCodesEnum;
import net.onebean.api.mngt.service.UagInfoCacheService;
import net.onebean.api.mngt.vo.GetAccTokenVo;
import net.onebean.component.redis.IRedisService;
import net.onebean.core.error.BusinessException;
import net.onebean.core.form.Parse;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UagInfoCacheServiceImpl implements UagInfoCacheService {

    @Autowired
    private IRedisService iRedisService;


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
