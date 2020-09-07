package net.onebean.gateway.dao;


import net.onebean.gateway.vo.UnLoginAccessApiWhiteListVo;
import net.onebean.gateway.model.UnLoginAccessApiWhiteList;
import net.onebean.gateway.vo.UagUnLoginAccessWhiteSyncListVo;
import net.onebean.core.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Icc
* @description 未登录访问API白名单 Dao
* @date 2019-06-28 11:02:32
*/
public interface UnLoginAccessApiWhiteListDao extends BaseDao<UnLoginAccessApiWhiteList> {

    /**
     * 查找未绑定数据
     * @param appId 应用id
     * @return list
     */
    List<UnLoginAccessApiWhiteListVo> findUnBindingData(@Param("appId") Integer appId, @Param("apiName") String apiName);

    /**
     * 查找一开小伙伴未登录访问白名单同步节点
     * @return list
     */
    List<UagUnLoginAccessWhiteSyncListVo> findUnLoginAccessWhiteListSyncList();
    /**
     * 查找私有令牌需要绑定的未登录白名单API
     * @return list
     */
    List<UnLoginAccessApiWhiteList> findPrivateTokenAuthWhiteList();
}