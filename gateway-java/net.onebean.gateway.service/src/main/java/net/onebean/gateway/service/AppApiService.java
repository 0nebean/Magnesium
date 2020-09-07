package net.onebean.gateway.service;


import net.onebean.gateway.vo.AppBindingApiReq;
import net.onebean.gateway.model.AppApi;
import net.onebean.core.base.IBaseBiz;

import java.util.List;


/**
* @author Icc
* @description app info join api info service
* @date 2019-01-25 20:13:35
*/

public interface AppApiService extends IBaseBiz<AppApi> {
    /**
     * 绑定API
     * @param req 参数体
     * @return bool
     */
    Boolean bindApi(AppBindingApiReq req);
    /**
     * 绑定API
     * @param req 参数体
     * @return bool
     */
    Boolean unBindApi(AppBindingApiReq req);
    /**
     * 查找私有令牌需要绑定的鉴权API
     * @return list
     */
    List<AppApi> findPrivateTokenAuthApiBind();
}