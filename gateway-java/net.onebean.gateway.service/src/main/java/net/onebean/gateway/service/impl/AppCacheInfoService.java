package net.onebean.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.vo.AppInfoSyncVo;
import net.onebean.component.redis.IRedisService;
import net.onebean.core.error.BusinessException;
import net.onebean.gateway.common.CacheConstants;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 应用信息
 * @author Icc
 */
@Service
public class AppCacheInfoService {

    @Autowired
    private IRedisService iRedisService;

    /**
     * 获取appInfo 信息
     * @param appId 应用ID
     * @return appInfo
     */
    public AppInfoSyncVo getAppInfoFromCache(String appId){
        AppInfoSyncVo appInfo;
        try {
            String appInfoStr = Optional.ofNullable(iRedisService.hGet(CacheConstants.UagScopeKeys.APP_INFO.getValue(),appId)).map(s -> s+"").orElse("");
            if (StringUtils.isEmpty(appInfoStr)){
                throw new BusinessException(ErrorCodesEnum.GET_CACHE_ERR.code(),ErrorCodesEnum.GET_CACHE_ERR.msg()+" key = "+CacheConstants.UagScopeKeys.APP_INFO.getValue() +" hkey = "+appId);
            }
            try {
                appInfo = JSON.parseObject(appInfoStr,AppInfoSyncVo.class);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.GET_CACHE_ERR.code(),ErrorCodesEnum.GET_CACHE_ERR.msg()+" key = "+CacheConstants.UagScopeKeys.APP_INFO.getValue() +" hkey = "+appId);
        }
        return appInfo;

    }

}
