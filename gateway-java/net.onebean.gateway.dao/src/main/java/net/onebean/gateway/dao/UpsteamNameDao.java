package net.onebean.gateway.dao;


import net.onebean.gateway.model.UpsteamName;
import net.onebean.core.base.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
* @author Icc
* @description upsteam name Dao
* @date 2019-03-01 17:40:12
*/
public interface UpsteamNameDao extends BaseDao<UpsteamName> {
    /**
     * 引用计数
     * @param upsteamName name
     * @return count
     */
    Integer countUseByUpsteamName(@Param("upsteamName") String upsteamName);
}