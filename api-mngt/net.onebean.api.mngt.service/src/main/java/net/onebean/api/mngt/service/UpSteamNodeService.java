package net.onebean.api.mngt.service;


import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.model.UpSteamNode;
import net.onebean.api.mngt.vo.*;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author 0neBean
* @description upsteam node info service
* @date 2019-03-01 15:25:32
*/

public interface UpSteamNodeService extends IBaseBiz<UpSteamNode> {
    /**
     * 查询vo
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<UpSteamNodeVo> findUpSteamNodeVo(UpSteamNodeAddReq param, Pagination page, Sort sort);
    /**
     * find By Id
     * @param id 主键
     * @return vo
     */
    UpSteamNodeVo findVoById(Object id);
    /**
     * 新增
     * @param request req
     * @return id
     */
    Long add(UpSteamNodeAddReq request);
    /**
     * 更新
     * @param request req
     * @return id
     */
    Integer update(UpSteamNodeModifyReq request);
    /**
     * 是否重复
     * @param physicalPath path
     * @return bool
     */
    Boolean isRepeatByPhysicalPath(String physicalPath,Object id);
    /**
     * 查找同步节点
     * @return list
     */
    List<UpSteamSyncNodeVo> findSyncNode();
    /**
     * 根据node名称查询可用版本号
     * @param request 参数
     * @return list
     */
    List<UpSteamVersionVo> findVersionListByNodeName(FindVersionListByNodeNameReq request);
    /**
     * 更新服务的选中版本
     * @param req 参数
     * @return bool
     */
    Boolean updateSelectedVersion(UpdateSelectedVersionReq req);
    /**
     * 添加节点信息
     * @param vo 参数
     * @return bool
     */
    Boolean addByDevOpsK8sNotificationVo(DevOpsK8sNotificationVo vo);
    /**
     * 删除节点信息
     * @param orig 参数
     * @return bool
     */
    Boolean setRunningStatusDownByDevOpsK8sNotificationVo(SetRunningStatusDown orig);
    /**
     * 是否重复的 k8s 推送
     * @param param 参数
     * @return bool
     */
    Boolean isRepeatByDevOpsK8sNotificationVo(DevOpsK8sNotificationVo param);
    /**
     * 停止警告 禁止被切换小于5分钟内的节点被关闭
     * @param id 主键
     * @return bool
     */
    void stopWarning(String id);
}