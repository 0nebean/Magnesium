package net.onebean.api.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.common.CacheConstants;
import net.onebean.api.mngt.common.ErrorCodesEnum;
import net.onebean.api.mngt.dao.UnLoginAccessApiWhiteListDao;
import net.onebean.api.mngt.service.ApiInfoService;
import net.onebean.api.mngt.service.AppInfoService;
import net.onebean.api.mngt.service.AppServerService;
import net.onebean.api.mngt.service.IpWhiteListService;
import net.onebean.api.mngt.vo.*;
import net.onebean.component.redis.IRedisService;
import net.onebean.core.error.BusinessException;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppServerServiceImpl implements AppServerService {


    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private ApiInfoService apiInfoService;
    @Autowired
    private UnLoginAccessApiWhiteListDao unLoginAccessApiWhiteListDao;
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private IpWhiteListService ipWhiteListService;


    private final static Logger logger = LoggerFactory.getLogger(net.onebean.api.mngt.service.impl.AppServerServiceImpl.class);

    @Override
    public Boolean syncAppApiRelationship() {

        Map<String, Object> map = new HashMap<>();

        AppApiRelationSyncResqVo appApiRelationSyncResqVo = apiInfoService.findSyncAppApiRelationship();
        List<AppApiRelationshipAppVo> appApi = Optional.of(appApiRelationSyncResqVo).map(AppApiRelationSyncResqVo::getAppApiList).orElse(null);
        logger.debug("AppApiServiceImpl method syncAppApiRelationship appApi = " + JSON.toJSONString(appApi, SerializerFeature.WriteMapNullValue));
        List<UriApiRelationshipVo> uriApi = Optional.of(appApiRelationSyncResqVo).map(AppApiRelationSyncResqVo::getUriApiList).orElse(null);
        logger.debug("AppApiServiceImpl method syncAppApiRelationship uriApi = " + JSON.toJSONString(uriApi, SerializerFeature.WriteMapNullValue));


        iRedisService.del(CacheConstants.APP_API_RELATION);
        if (CollectionUtil.isNotEmpty(appApi)) {
            //组装map
            for (AppApiRelationshipAppVo re : appApi) {
                String openId = Optional.ofNullable(re).map(AppApiRelationshipAppVo::getOpenId).orElse(null);
                List<String> apis = Optional.ofNullable(re).map(AppApiRelationshipAppVo::getApiIds).orElse(null);
                if (StringUtils.isEmpty(openId)) {
                    throw new BusinessException(ErrorCodesEnum.DIRTY_DATA_ERR.code(), ErrorCodesEnum.DIRTY_DATA_ERR.msg() + "field of openId");
                }
                if (CollectionUtil.isEmpty(apis)) {
                    throw new BusinessException(ErrorCodesEnum.DIRTY_DATA_ERR.code(), ErrorCodesEnum.DIRTY_DATA_ERR.msg() + "field of apis");
                }
                map.put(openId, JSON.toJSONString(apis));
            }
            logger.debug("AppApiServiceImpl syncAppApiRelationship method map1 = " + JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));

            try {
                iRedisService.hSetAll(CacheConstants.APP_API_RELATION, map);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(), ErrorCodesEnum.PUT_CACHE_ERR.msg() + CacheConstants.APP_API_RELATION);
            }
        }

        map = new HashMap<>();
        iRedisService.del(CacheConstants.URI_API_RELATION);
        if (CollectionUtil.isNotEmpty(uriApi)) {
            for (UriApiRelationshipVo u : uriApi) {
                String path = Optional.ofNullable(u).map(UriApiRelationshipVo::getPath).orElse(null);
                ApiInfoVo apiInfo = Optional.ofNullable(u).map(UriApiRelationshipVo::getApiInfo).orElse(null);
                if (StringUtils.isEmpty(path)) {
                    throw new BusinessException(ErrorCodesEnum.DIRTY_DATA_ERR.code(), ErrorCodesEnum.DIRTY_DATA_ERR.msg() + "field of path");
                }
                if (null == apiInfo) {
                    throw new BusinessException(ErrorCodesEnum.DIRTY_DATA_ERR.code(), ErrorCodesEnum.DIRTY_DATA_ERR.msg() + "field of apiInfo");
                }
                map.put(path, JSON.toJSONString(apiInfo));
            }
            logger.debug("AppApiServiceImpl syncAppApiRelationship method map2 = " + JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));

            try {
                iRedisService.hSetAll(CacheConstants.URI_API_RELATION, map);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(), ErrorCodesEnum.PUT_CACHE_ERR.msg() + CacheConstants.APP_API_RELATION);
            }
        }

        map = new HashMap<>();

        List<IpWhiteListVo> ipWhiteVoList = ipWhiteListService.findSyncList();
        List<AppInfoSyncCloudResp> appInfos = appInfoService.findBindAppInfo();
        List<UagUnLoginAccessWhiteSyncListVo> unLoginAccessWhiteListSyncList = unLoginAccessApiWhiteListDao.findUnLoginAccessWhiteListSyncList();

        iRedisService.del(CacheConstants.IP_ADDRESS_WHITE_LIST);
        if (CollectionUtil.isNotEmpty(appInfos)) {
            for (AppInfoSyncCloudResp app : appInfos) {
                String appId = Optional.ofNullable(app).map(AppInfoSyncCloudResp::getAppId).orElse(null);
                AppInfoSyncVo appInfo = Optional.ofNullable(app).map(AppInfoSyncCloudResp::getAppInfo).orElse(null);
                if (StringUtils.isEmpty(appId)) {
                    throw new BusinessException(ErrorCodesEnum.DIRTY_DATA_ERR.code(), ErrorCodesEnum.DIRTY_DATA_ERR.msg() + "field of path");
                }
                if (null == appInfo) {
                    throw new BusinessException(ErrorCodesEnum.DIRTY_DATA_ERR.code(), ErrorCodesEnum.DIRTY_DATA_ERR.msg() + "field of apiInfo");
                }
                map.put(appId, JSON.toJSONString(appInfo));
            }
            logger.debug("AppApiServiceImpl syncAppApiRelationship method map3 = " + JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));

            try {
                iRedisService.del(CacheConstants.APP_INFO);
                iRedisService.hSetAll(CacheConstants.APP_INFO, map);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(), ErrorCodesEnum.PUT_CACHE_ERR.msg() + CacheConstants.APP_API_RELATION);
            }
        }

        if (CollectionUtil.isNotEmpty(ipWhiteVoList)) {
            try {
                iRedisService.set(CacheConstants.IP_ADDRESS_WHITE_LIST, JSON.toJSONString(ipWhiteVoList));
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(), ErrorCodesEnum.PUT_CACHE_ERR.msg() + CacheConstants.APP_API_RELATION);
            }
        }

        map = new HashMap<>();
        iRedisService.del(CacheConstants.UAG_OPENAPI_UNLOGIN_ACCESS_WHITELIST_API);
        if (CollectionUtil.isNotEmpty(unLoginAccessWhiteListSyncList)) {
            for (UagUnLoginAccessWhiteSyncListVo whiteSyncListVo : unLoginAccessWhiteListSyncList) {
                String appId = Optional.ofNullable(whiteSyncListVo).map(UagUnLoginAccessWhiteSyncListVo::getAppId).orElse(null);
                List<RsSalesUnLoginAccessWhiteVo> whiteVoList = Optional.ofNullable(whiteSyncListVo).map(UagUnLoginAccessWhiteSyncListVo::getRsSalesUnLoginAccessWhiteListVos).orElse(null);
                if (CollectionUtil.isNotEmpty(whiteVoList)) {
                    map.put(appId, JSONArray.toJSONString(whiteVoList));
                }
            }
            logger.debug("AppApiServiceImpl syncAppApiRelationship method map4 = " + JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));
            try {
                iRedisService.hSetAll(CacheConstants.UAG_OPENAPI_UNLOGIN_ACCESS_WHITELIST_API, map);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(), ErrorCodesEnum.PUT_CACHE_ERR.msg() + CacheConstants.APP_API_RELATION);
            }
        }

        return true;
    }
}
