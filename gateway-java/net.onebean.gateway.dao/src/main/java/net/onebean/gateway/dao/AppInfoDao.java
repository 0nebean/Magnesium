package net.onebean.gateway.dao;

import net.onebean.gateway.vo.AppInfoSyncCloudResp;
import net.onebean.gateway.model.AppInfo;
import net.onebean.core.base.BaseDao;

import java.util.List;

/**
* @author Icc
* @description 应用信息 Dao
* @date 2019-01-03 16:14:09
*/
public interface AppInfoDao extends BaseDao<AppInfo> {
    /**
     * 查找绑定了接口的app信息
     * @return list
     */
    List<AppInfoSyncCloudResp>findBindAppInfo();
}