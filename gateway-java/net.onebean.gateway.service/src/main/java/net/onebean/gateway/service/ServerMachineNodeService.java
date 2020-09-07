package net.onebean.gateway.service;


import net.onebean.gateway.vo.ServerMachineNodeAddReq;
import net.onebean.gateway.vo.ServerMachineNodeInfoModifyReq;
import net.onebean.gateway.vo.ServerMachineNodeVo;
import net.onebean.gateway.model.ServerMachineNode;
import net.onebean.gateway.vo.ServerMachineNodeSyncVo;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author Icc
* @description 服务器节点 service
* @date 2019-11-01 14:19:09
*/

public interface ServerMachineNodeService extends IBaseBiz<ServerMachineNode> {
    /**
     * 查找list
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<ServerMachineNodeVo> findServerMachineNodeVo(ServerMachineNodeAddReq param, Pagination page, Sort sort);
    /**
     * 查找VO
     * @param id 主键
     * @return vo
     */
    ServerMachineNodeVo findVoById(Object id);
    /**
     * 新增
     * @param request req
     * @return id
     */
    Long add(ServerMachineNodeAddReq request);
    /**
     * 更新
     * @param request req
     * @return id
     */
    Integer update(ServerMachineNodeInfoModifyReq request);
    /**
     * 是否重复
     * @param ipAddress ip
     * @param id id
     * @return bool
     */
    Boolean isRepeatByIpAddressAndId(String ipAddress,Object id);
    /**
     * 查找需要同步的nginx节点
     * @return list
     */
    List<ServerMachineNodeSyncVo> findSyncOpenrestyEndPoint();
    /**
     * 查找需要同步的k8s节点
     * @return list
     */
    List<ServerMachineNodeSyncVo> findSyncKubernetesEndPoint();
}