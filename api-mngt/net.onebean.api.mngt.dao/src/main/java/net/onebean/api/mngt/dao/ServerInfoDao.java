package net.onebean.api.mngt.dao;


import net.onebean.core.base.BaseDao;
import net.onebean.api.mngt.vo.ServerHostNodeVo;
import net.onebean.api.mngt.model.ServerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 0neBean
* @description server info Dao
* @date 2019-01-21 18:05:28
*/
public interface ServerInfoDao extends BaseDao<ServerInfo> {
    /**
     * 根据serverId 查找绑定的appName
     * @param serverId 服务ID
     * @return appName
     */
    String findBindAppNameByServerId(@Param("serverId")Long serverId);
    /**
     * 获取相同节点名不为自己的数据数量
     * @param upSteamNodeName 节点名
     * @param id 主键
     * @return int
     */
    Integer countServerInfoByUpSteamNodeName(@Param("upSteamNodeName")String upSteamNodeName,@Param("id")Long id);
    /**
     * 查询服务关联的节点IDs
     * @param serverName 服务名
     * @return 节点ids
     */
    List<String> findUpSteamNodeIdsByServerName(@Param("serverName") String serverName);
    /**
     * 查找所有需要同步的域名配置节点
     * @return list
     */
    List<ServerHostNodeVo> findSyncHostNode();
}