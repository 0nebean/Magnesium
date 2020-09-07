package net.onebean.gateway.service;


import net.onebean.core.base.IBaseBiz;
import net.onebean.gateway.model.PrivateAuthAppInfo;
import net.onebean.gateway.vo.FindUagUserAppListResp;

import java.util.List;


/**
* @author Icc
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