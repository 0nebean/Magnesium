package net.onebean.api.mngt.dao;


import net.onebean.core.base.BaseDao;
import net.onebean.api.mngt.vo.UpSteamSyncNodeVo;
import net.onebean.api.mngt.model.UpSteamNode;
import net.onebean.api.mngt.api.model.FindVersionListByNodeNameReq;
import net.onebean.api.mngt.api.model.UpSteamVersionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 0neBean
* @description upsteam node info Dao
* @date 2019-03-01 15:25:32
*/
public interface UpSteamNodeDao extends BaseDao<UpSteamNode> {
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
     * 更新选中的版本
     * @param selectedVersion 选中的版本
     * @param upSteamNodeName 节点名
     * @return int
     */
    Integer updateSelectedVersion(@Param("selectedVersion") String selectedVersion, @Param("upSteamNodeName") String upSteamNodeName);

    /**
     * 统计数量 根据 k8s 推送
     * @param nodeName 节点名
     * @param currentVersion 当前版本
     * @param nodeNamespace 命名空间
     * @return int
     */
    Integer countByDevOpsK8sNotificationVo(@Param("nodeName") String nodeName, @Param("currentVersion") String currentVersion, @Param("nodeNamespace") String nodeNamespace);
    /**
     * 更新选中版本之外的节点弃用时间
     * @param selectedVersion 选中版本
     * @param upSteamNodeName 节点名
     */
    void updateNodeDeprecatedTime(@Param("selectedVersion") String selectedVersion, @Param("upSteamNodeName") String upSteamNodeName);
    /**
     * 统计当前数据的弃用时间 是否小于5分钟
     * @param id 主键
     * @return int
     */
    Integer countStopTimeLessThan5Min(@Param("id") String id);
}