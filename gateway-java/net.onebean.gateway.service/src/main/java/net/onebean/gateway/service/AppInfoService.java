package net.onebean.gateway.service;

import net.onebean.gateway.vo.AppInfoQueryRequest;
import net.onebean.gateway.vo.AppInfoSyncCloudResp;
import net.onebean.gateway.vo.AppInfoVo;
import net.onebean.gateway.vo.FindAppByAppIdAndSecretReq;
import net.onebean.gateway.model.AppInfo;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author Icc
* @description 应用信息 service
* @date 2019-01-03 16:14:09
*/
public interface AppInfoService extends IBaseBiz<AppInfo> {
    /**
     * 根据vo中的条件查找list
     * @param param 参数
     * @param pagination 分页
     * @return list
     */
    List<AppInfoVo> findByAppInfoQueryRequest(AppInfoQueryRequest param, Pagination pagination, Sort sort);
    /**
     * 根据id查找 vo
     * @param id 主键
     * @return vo
     */
    AppInfoVo findVoById(Object id);
    /**
     * 根据id查找 vo
     * @param appId appId
     * @return vo
     */
    AppInfoVo findVoByAppId(Object appId);
    /**
     * 查找当前app登录类型
     * @param appId appId
     * @return LoginType
     */
    String getCurrentAppLoginType(Object appId);
    /**
     * 生成应用ID及secret
     * @param appInfo 应用
     */
    void addAppInfo(AppInfo appInfo);
    /**
     * 根据appid secret 查找应用
     * @param req 参数体
     * @return APP vo
     */
    AppInfoVo findByAppIdAndSecret(FindAppByAppIdAndSecretReq req);
    /**
     * 查找绑定了接口的app信息
     * @return list
     */
    List<AppInfoSyncCloudResp> findBindAppInfo();
}