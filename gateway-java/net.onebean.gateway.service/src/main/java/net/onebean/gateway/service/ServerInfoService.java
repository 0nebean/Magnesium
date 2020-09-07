package net.onebean.gateway.service;


import net.onebean.gateway.vo.FindServerByNameReq;
import net.onebean.gateway.vo.ServerInfoAddReq;
import net.onebean.gateway.vo.ServerInfoModifyReq;
import net.onebean.gateway.vo.ServerInfoVo;
import net.onebean.gateway.model.ServerInfo;
import net.onebean.gateway.vo.ServerHostNodeVo;
import net.onebean.gateway.vo.UpSteamSyncNodeVo;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author Icc
* @description server info service
* @date 2019-01-21 18:05:28
*/

public interface ServerInfoService extends IBaseBiz<ServerInfo> {
    /**
     * 查询vo list
     * @param req 参数体
     * @return list
     */
    List<ServerInfoVo> findServerInfo(ServerInfoAddReq req, Pagination pagination, Sort sort);
    /**
     * 查询vo list
     * @param req 参数体
     * @return list
     */
    List<ServerInfoVo> findServerInfo(FindServerByNameReq req);
    /**
     *  根据Id 查找VO
     * @param id 主键
     * @return vo
     */
    ServerInfoVo findVoById(Object id);
    /**
     * 删除服务信息
     * @param request 请求体
     * @return bool
     */
    Boolean deleteServerInfo(ServerInfoModifyReq request);
    /**
     * 保存服务信息
     * @param request 服务信息
     * @return bool
     */
    Boolean saveServerInfoAddReq(ServerInfoAddReq request);
    /**
     * 是否是重复的 server info
     * @param upSteamNodeName 节点名称
     * @param id 主键 可以为 null
     * @return bool
     */
    Boolean isRepeatServerInfoByUpSteamNodeName(String upSteamNodeName,Long id);
    /**
     * 查询服务关联的节点IDs
     * @param serverName 服务名
     * @return 节点ids
     */
    List<String> findUpSteamNodeIdsByServerName(String serverName);
    /**
     * 更新
     * @param request 参数
     * @return bool
     */
    Boolean updateServerInfoModifyReq(ServerInfoModifyReq request);
    /**
     * 查找所有需要同步的域名配置节点
     * @param upSteamNodeVos upSteamNodeVos
     * @return list
     */
    List<ServerHostNodeVo> findSyncHostNode(List<UpSteamSyncNodeVo> upSteamNodeVos);
}