package net.onebean.api.mngt.service;


import net.onebean.api.mngt.model.ApiInfo;
import net.onebean.api.mngt.model.AppApi;
import net.onebean.api.mngt.api.model.AppBindingApiReq;
import net.onebean.core.base.IBaseBiz;

import java.util.List;


/**
* @author 0neBean
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