package net.onebean.user.mngt.service;


import net.onebean.core.base.IBaseBiz;
import net.onebean.user.mngt.api.model.FindUagUserAppListResp;
import net.onebean.user.mngt.model.PrivateAuthAppInfo;

import java.util.List;


/**
* @author 0neBean
* @description 私有令牌应用 service
* @date 2020-07-03 21:15:42
*/

public interface PrivateAuthAppInfoService extends IBaseBiz<PrivateAuthAppInfo> {

    /**
     * 查找私有令牌应用列表
     *
     * @return list
     */
    List<FindUagUserAppListResp> findAll2FindUagUserAppListResp();
}