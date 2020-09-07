package net.onebean.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.common.PassswordGetter;
import net.onebean.gateway.dao.AppInfoDao;
import net.onebean.gateway.enumModel.AuthTypeEnum;
import net.onebean.gateway.model.AppApi;
import net.onebean.gateway.model.AppInfo;
import net.onebean.gateway.model.UnLoginAccessApiWhiteList;
import net.onebean.gateway.service.AppApiService;
import net.onebean.gateway.service.AppInfoService;
import net.onebean.gateway.service.UagUserInfoService;
import net.onebean.gateway.service.UnLoginAccessApiWhiteListService;
import net.onebean.gateway.vo.*;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import net.onebean.gateway.vo.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Icc
 * @description 应用信息 serviceImpl
 * @date 2019-01-03 16:14:09
 */
@Service
public class AppInfoServiceImpl extends BaseBiz<AppInfo, AppInfoDao> implements AppInfoService {


    private final static Logger logger = LoggerFactory.getLogger(AppInfoServiceImpl.class);

    @Autowired
    private AppApiService appApiService;
    @Autowired
    private UnLoginAccessApiWhiteListService unLoginAccessWhiteListService;
    @Autowired
    private UagUserInfoService uagUserInfoService;


    @Override
    public List<AppInfoVo> findByAppInfoQueryRequest(AppInfoQueryRequest param, Pagination pagination, Sort sort) {
        String id = Optional.ofNullable(param).map(AppInfoQueryRequest::getId).orElse(null);
        String appName = Optional.ofNullable(param).map(AppInfoQueryRequest::getAppName).orElse(null);
        String appStatus = Optional.ofNullable(param).map(AppInfoQueryRequest::getAppStatus).orElse(null);
        String authType = Optional.ofNullable(param).map(AppInfoQueryRequest::getAuthType).orElse(null);
        List<Condition> paramList = new ArrayList<>();
        if (StringUtils.isNotEmpty(id)) {
            Condition condition = Condition.parseCondition("id@int@eq");
            condition.setValue(id);
            paramList.add(condition);
        }
        if (StringUtils.isNotEmpty(appName)) {
            Condition condition = Condition.parseCondition("app_name@string@like");
            condition.setValue(appName);
            paramList.add(condition);
        }
        if (StringUtils.isNotEmpty(appStatus)) {
            Condition condition = Condition.parseCondition("app_status@string@eq");
            condition.setValue(appStatus);
            paramList.add(condition);
        }
        if (StringUtils.isNotEmpty(authType)) {
            Condition condition = Condition.parseModelCondition("authType@string@eq");
            condition.setValue(appStatus);
            paramList.add(condition);
        }

        List<AppInfo> list = this.find(pagination, paramList, sort);
        logger.debug("AppInfoServiceImpl method findByAppInfoQueryRequest list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<AppInfoVo> res = JSON.parseArray(JSON.toJSONString(list), AppInfoVo.class);
        logger.debug("AppInfoServiceImpl method findByAppInfoQueryRequest res = " + JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }

    @Override
    public AppInfoVo findVoById(Object id) {

        AppInfo appInfo = this.findById(id);
        AppInfoVo appInfoVo = new AppInfoVo();
        if (null != appInfo) {
            try {
                BeanUtils.copyProperties(appInfoVo, appInfo);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg());
            }
        } else {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        return appInfoVo;
    }

    @Override
    public AppInfoVo findVoByAppId(Object appId) {
        Condition condition = Condition.parseModelCondition("openId@string@eq");
        condition.setValue(appId);
        List<AppInfo> appInfos = this.find(null,condition);
        AppInfoVo appInfoVo = new AppInfoVo();
        if (CollectionUtil.isNotEmpty(appInfos)) {
            try {
                BeanUtils.copyProperties(appInfoVo, appInfos.get(0));
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg());
            }
        } else {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        return appInfoVo;
    }

    @Override
    public void addAppInfo(AppInfo appInfo) {

        /*设置openApi相关信息*/
        String authType = Optional.ofNullable(appInfo).map(AppInfo::getAuthType).orElse("");
        String appName = Optional.ofNullable(appInfo).map(AppInfo::getAppName).orElse("");
        String appId = System.currentTimeMillis() + "";
        appId = appId.substring(2);
        String secret = PassswordGetter.generatePassword(appId.length() * 3 - 1);
        secret = DigestUtils.md5Hex(secret);
        assert appInfo != null;
        appInfo.setOpenId(appId);
        if (authType.equals(AuthTypeEnum.IP_AND_TOKEN.getKey())) {
            appInfo.setInnerApiToken(secret);
        } else {
            appInfo.setSecret(secret);
        }
        this.save(appInfo);
        String appOpenId = Optional.of(appInfo).map(AppInfo::getOpenId).orElse("");
        if (authType.equals(AuthTypeEnum.OAUTH_PRIVATE_CODE.getKey())) {
            //初始化用户表数据
            InitUagAccountTableReq initUagAccountTableReq = new InitUagAccountTableReq();
            initUagAccountTableReq.setAppId(appOpenId);
            initUagAccountTableReq.setAppName(appName);
            Boolean flag = uagUserInfoService.initUagAccountTable(initUagAccountTableReq);
            if (!flag) {
                throw new BusinessException(ErrorCodesEnum.CLOUD_API_ERROR.code(), ErrorCodesEnum.CLOUD_API_ERROR.msg() + "call initUagAccountTable method failure ");
            }

            //初始化 私有令牌绑定的授权API
            List<AppApi> privateTokenAuthApiBind = appApiService.findPrivateTokenAuthApiBind();
            List<UnLoginAccessApiWhiteList> privateTokenAuthWhiteList = unLoginAccessWhiteListService.findPrivateTokenAuthWhiteList();
            privateTokenAuthApiBind.forEach(o -> o.setAppId(Parse.toInt(appInfo.getId())));
            privateTokenAuthWhiteList.forEach(o -> o.setAppId(Parse.toInt(appInfo.getId())));
            appApiService.saveBatch(privateTokenAuthApiBind);
            unLoginAccessWhiteListService.saveBatch(privateTokenAuthWhiteList);
        }
    }


    @Override
    public AppInfoVo findByAppIdAndSecret(FindAppByAppIdAndSecretReq req) {

        String openId = Optional.ofNullable(req).map(FindAppByAppIdAndSecretReq::getOpenId).orElse(null);
        String secret = Optional.ofNullable(req).map(FindAppByAppIdAndSecretReq::getSecret).orElse(null);
        if (StringUtils.isEmpty(openId)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + "filed of openId");
        }

        if (StringUtils.isEmpty(secret)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + "filed of secret");
        }

        Condition param1 = Condition.parseModelCondition("openId@string@eq");
        Condition param2 = Condition.parseModelCondition("secret@string@eq");
        param1.setValue(openId);
        param2.setValue(secret);

        List<AppInfo> list = this.find(null, new ArrayList<Condition>() {{
            add(param1);
            add(param2);
        }});

        if (CollectionUtil.isEmpty(list)) {
            return null;
        }

        AppInfoVo resp = new AppInfoVo();
        try {
            BeanUtils.copyProperties(resp, list.get(0));
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg() + "filed of secret");
        }
        return resp;
    }

    @Override
    public List<AppInfoSyncCloudResp> findBindAppInfo() {
        return baseDao.findBindAppInfo();
    }

    @Override
    public Integer deleteById(Object id) {
        /*删除api app 关联关系*/
        Condition param = Condition.parseModelCondition("appId@string@eq");
        param.setValue(id);
        appApiService.deleteByIds(appApiService.find(null, param).stream().map(AppApi::getId).collect(Collectors.toList()));
        /*删除应用未登录 API访问白名单*/
        Condition condition = Condition.parseModelCondition("appId@string@eq");
        condition.setValue(id);
        unLoginAccessWhiteListService.deleteByIds(unLoginAccessWhiteListService.find(null, condition).stream().map(UnLoginAccessApiWhiteList::getId).collect(Collectors.toList()));
        return super.deleteById(id);
    }


    @Override
    public String getCurrentAppLoginType(Object appId) {
        AppInfoVo vo = findVoByAppId(appId);
        String loginType = Optional.ofNullable(vo).map(AppInfoVo::getLoginType).orElse("");
        if (StringUtils.isEmpty(loginType)) {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        return loginType;
    }
}