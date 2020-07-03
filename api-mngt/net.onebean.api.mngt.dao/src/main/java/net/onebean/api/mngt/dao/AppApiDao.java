package net.onebean.api.mngt.dao;


import net.onebean.core.base.BaseDao;
import net.onebean.api.mngt.model.AppApi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 0neBean
* @description app info join api info Dao
* @date 2019-01-25 20:13:35
*/
public interface AppApiDao extends BaseDao<AppApi> {
    /**
     * 根据AppId删除绑定关系
     * @param appId 应用ID
     * @param ids api ids
     * @return 是否成功
     */
    Integer deleteByAppidAndApiIds(@Param("appId") String appId, @Param("apiIds") List<String> ids);
    /**
     * 查找私有令牌需要绑定的鉴权API
     * @return list
     */
    List<AppApi> findPrivateTokenAuthApiBind();
}