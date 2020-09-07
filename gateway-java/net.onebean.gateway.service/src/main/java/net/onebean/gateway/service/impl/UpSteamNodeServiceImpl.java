package net.onebean.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.base.YesOrNoEnum;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.dao.UpSteamNodeDao;
import net.onebean.gateway.enumModel.DeployTypeEnum;
import net.onebean.gateway.enumModel.ProgramTypeEnum;
import net.onebean.gateway.enumModel.RunningStatusEnum;
import net.onebean.gateway.model.UpSteamNode;
import net.onebean.gateway.service.DevOpsKubernetesService;
import net.onebean.gateway.service.ServerMachineNodeService;
import net.onebean.gateway.service.UpSteamNodeService;
import net.onebean.gateway.service.UpsteamNameService;
import net.onebean.gateway.vo.*;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import net.onebean.gateway.vo.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author Icc
 * @description upsteam node info serviceImpl
 * @date 2019-03-01 15:25:32
 */
@Service
public class UpSteamNodeServiceImpl extends BaseBiz<UpSteamNode, UpSteamNodeDao> implements UpSteamNodeService {

    private final static Logger logger = LoggerFactory.getLogger(UpSteamNodeServiceImpl.class);
    private final static String SELECTED_VERSION_EQ = "selected-version-eq";
    private final static String CURRENT_VERSION_OLD = "current-version-old";
    private final static String CURRENT_VERSION_YOUNG = "current-version-young";


    @Autowired
    private ServerMachineNodeService serverMachineNodeService;
    @Autowired
    private UpsteamNameService upsteamNameService;
    @Autowired
    private DevOpsKubernetesService devOpsKubernetesService;


    @Override
    public List<UpSteamNodeVo> findUpSteamNodeVo(UpSteamNodeAddReq param, Pagination page, Sort sort) {

        String nodeName = Optional.ofNullable(param).map(UpSteamNodeAddReq::getNodeName).orElse(null);
        String nodeNamespace = Optional.ofNullable(param).map(UpSteamNodeAddReq::getNodeNamespace).orElse(null);
        String currentVersionParam = Optional.ofNullable(param).map(UpSteamNodeAddReq::getNodeNamespace).orElse(null);
        String deployTypeParam = Optional.ofNullable(param).map(UpSteamNodeAddReq::getDeployType).orElse(null);
        String runningStatusParam = Optional.ofNullable(param).map(UpSteamNodeAddReq::getRunningStatus).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(nodeName)) {
            Condition condition = Condition.parseModelCondition("nodeName@string@like");
            condition.setValue(nodeName);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(nodeNamespace)) {
            Condition condition = Condition.parseModelCondition("nodeNamespace@string@like");
            condition.setValue(nodeNamespace);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(currentVersionParam)) {
            Condition condition = Condition.parseModelCondition("currentVersion@string@eq");
            condition.setValue(currentVersionParam);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(deployTypeParam)) {
            Condition condition = Condition.parseModelCondition("deployType@string@eq");
            condition.setValue(deployTypeParam);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(runningStatusParam)) {
            Condition condition = Condition.parseModelCondition("runningStatus@string@eq");
            condition.setValue(runningStatusParam);
            paramList.add(condition);
        }

        List<UpSteamNode> list = this.find(page, paramList, sort);
        logger.debug("UpSteamNodeServiceImpl method findUpSteamNode list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<UpSteamNodeVo> res = JSON.parseArray(JSON.toJSONString(list), UpSteamNodeVo.class);
        logger.debug("UpSteamNodeServiceImpl method findUpSteamNode res = " + JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        res = res.stream().peek(o -> {
            String deployType = Optional.of(o).map(UpSteamNodeVo::getDeployType).orElse("");
            if (deployType.equals(DeployTypeEnum.PHYSICAL.getKey())) {
                o.setNodeNamespace("-");
                o.setCurrentVersion("-");
                o.setSelectedVersion("-");
                o.setRunningStatus("-");
            } else {
                String runningStatus = Optional.ofNullable(o).map(UpSteamNodeVo::getRunningStatus).orElse("");
                if (runningStatus.equals(RunningStatusEnum.RUNNING.getKey())) {
                    String currentVersion = Optional.ofNullable(o).map(UpSteamNodeVo::getCurrentVersion).orElse("");
                    String selectedVersion = Optional.ofNullable(o).map(UpSteamNodeVo::getSelectedVersion).orElse("");
                    UpSteamNodeCellClassNameVo cellClassNameVo = new UpSteamNodeCellClassNameVo();
                    //新部署还没有被设置选中版本
                    if (StringUtils.isEmpty(selectedVersion) && StringUtils.isNotEmpty(currentVersion)) {
                        cellClassNameVo.setRunningStatus(CURRENT_VERSION_YOUNG);
                        o.setCanStop(YesOrNoEnum.Yes.getKey());
                    } else {
                        int compareResult = StringUtils.compareVersion(StringUtils.getUagVersionFromTagStr(selectedVersion), StringUtils.getUagVersionFromTagStr(currentVersion));
                        switch (compareResult) {
                            case 0:
                                cellClassNameVo.setRunningStatus(SELECTED_VERSION_EQ);
                                o.setCanStop(YesOrNoEnum.NO.getKey());
                                break;
                            case 1:
                                cellClassNameVo.setRunningStatus(CURRENT_VERSION_OLD);
                                o.setCanStop(YesOrNoEnum.Yes.getKey());
                                break;
                            case -1:
                                cellClassNameVo.setRunningStatus(CURRENT_VERSION_YOUNG);
                                o.setCanStop(YesOrNoEnum.Yes.getKey());
                                break;
                            default:
                                break;
                        }
                    }
                    o.setCellClassName(cellClassNameVo);
                }
            }

        }).collect(Collectors.toList());

        return res;
    }

    @Override
    public UpSteamNodeVo findVoById(Object id) {
        UpSteamNode UpSteamNode = this.findById(id);
        UpSteamNodeVo UpSteamNodeVo = new UpSteamNodeVo();
        if (null != UpSteamNode) {
            try {
                BeanUtils.copyProperties(UpSteamNodeVo, UpSteamNode);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg() + "bean of UpSteamNodeVo");
            }
        } else {
            return null;
        }
        return UpSteamNodeVo;
    }

    @Override
    public Boolean isRepeatByPhysicalPath(String physicalPath, Object id) {
        List<Condition> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(physicalPath)) {
            Condition condition = Condition.parseModelCondition("physicalPath@string@eq");
            condition.setValue(physicalPath);
            param.add(condition);
        }
        if (StringUtils.isNotEmpty(id)) {
            Condition condition = Condition.parseModelCondition("id@string@nq");
            condition.setValue(id);
            param.add(condition);
        }

        return CollectionUtil.isNotEmpty(this.find(null, param));
    }

    @Override
    public Boolean isRepeatByFilePath(String filePath, Object id) {
        List<Condition> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(filePath)) {
            Condition condition = Condition.parseModelCondition("filePath@string@eq");
            condition.setValue(filePath);
            param.add(condition);
        }
        if (StringUtils.isNotEmpty(id)) {
            Condition condition = Condition.parseModelCondition("id@string@nq");
            condition.setValue(id);
            param.add(condition);
        }

        return CollectionUtil.isNotEmpty(this.find(null, param));
    }

    @Override
    public Long add(UpSteamNodeAddReq request) {
        UpSteamNode node = new UpSteamNode();
        try {
            BeanUtils.copyProperties(node, request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg());
        }
        String physicalPath = Optional.ofNullable(request).map(UpSteamNodeAddReq::getPhysicalPath).orElse(null);
        String filePath = Optional.ofNullable(request).map(UpSteamNodeAddReq::getFilePath).orElse(null);
        if (StringUtils.isEmpty(physicalPath) && StringUtils.isEmpty(filePath)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }
        if (StringUtils.isNotEmpty(physicalPath) && isRepeatByPhysicalPath(physicalPath, null)) {
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(), ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        if (StringUtils.isNotEmpty(filePath) && isRepeatByFilePath(filePath, null)) {
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(), ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        this.save(node);
        return node.getId();
    }

    @Override
    public Integer update(UpSteamNodeModifyReq request) {
        UpSteamNode target = new UpSteamNode();

        try {
            BeanUtils.copyProperties(target, request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg());
        }

        logger.debug("UpSteamNodeServiceImpl update method target = " + JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));

        String physicalPath = Optional.ofNullable(request).map(UpSteamNodeModifyReq::getPhysicalPath).orElse(null);
        String filePath = Optional.ofNullable(request).map(UpSteamNodeModifyReq::getFilePath).orElse(null);
        if (StringUtils.isEmpty(physicalPath) && StringUtils.isEmpty(filePath)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }
        if (StringUtils.isNotEmpty(physicalPath) && isRepeatByPhysicalPath(physicalPath, null)) {
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(), ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        String id = Optional.of(request).map(UpSteamNodeModifyReq::getId).orElse(null);
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " field of id");
        }
        if (StringUtils.isNotEmpty(physicalPath) && isRepeatByPhysicalPath(physicalPath, id)) {
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(), ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        if (StringUtils.isNotEmpty(filePath) && isRepeatByFilePath(filePath, id)) {
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(), ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        return this.update(target);
    }

    @Override
    public List<UpSteamSyncNodeVo>
    findSyncNode() {
        return baseDao.findSyncNode();
    }

    @Override
    public List<UpSteamVersionVo> findVersionListByNodeName(FindVersionListByNodeNameReq request) {
        return baseDao.findVersionListByNodeName(request);
    }

    @Override
    public Boolean updateSelectedVersion(UpdateSelectedVersionReq req) {
        String selectedVersion = Optional.of(req).map(UpdateSelectedVersionReq::getSelectedVersion).orElse("");
        String upSteamNodeName = Optional.of(req).map(UpdateSelectedVersionReq::getUpsteamNodeName).orElse("");
        //更新选中版本之外的节点弃用时间
        baseDao.updateNodeDeprecatedTime(selectedVersion, upSteamNodeName);
        //更新节点选中版本信息
        baseDao.updateSelectedVersion(selectedVersion, upSteamNodeName);
        logger.info("updateSelectedVersion done!");
        return true;
    }


    @Override
    public Boolean addByDevOpsK8sNotificationVo(DevOpsK8sNotificationVo orig) {
        //如果版本不重复，增加该条记录
        if (this.isRepeatByDevOpsK8sNotificationVo(orig)) {
            logger.info("addNodeFromK8s repeat data , do nothing!");
            return true;
        }
        //装载数据库对象
        UpSteamNode dest = new UpSteamNode();
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg() + " of DevOpsK8sNotificationVo");
        }
        dest.setDeployType(DeployTypeEnum.KUBERNETES.getKey());

        //从k8s获取nodePort信息 获取k8s master conf
        ServerMachineNodeSyncVo k8sConf;
        List<ServerMachineNodeSyncVo> list = serverMachineNodeService.findSyncKubernetesEndPoint();

        if (CollectionUtil.isNotEmpty(list)) {
            //这里只取第一个
            k8sConf = Optional.of(list).map(l -> l.get(0)).orElse(null);
        } else {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg() + " get k8s master conf empty");
        }

        //获取k8s 上 pod nodePort信息
        K8sNodePortInfoVo response = devOpsKubernetesService.getPodNodePort(orig, k8sConf);
        String clusterip = Optional.ofNullable(response).map(K8sNodePortInfoVo::getClusterip).orElse("");
        String nodePort = Optional.ofNullable(response).map(K8sNodePortInfoVo::getNodePort).orElse("");
        if (StringUtils.isEmpty(clusterip) || StringUtils.isEmpty(nodePort)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " clusterip or nodePort");
        }
        clusterip = clusterip + ":" + nodePort;
        dest.setPhysicalPath(clusterip);
        //默认后端项目
        dest.setProgramType(ProgramTypeEnum.BACKED.getKey());
        String nodeName = Optional.ofNullable(orig).map(DevOpsK8sNotificationVo::getNodeName).orElse("");

        //如果 没有upSteamName 需要创建
        if (!upsteamNameService.isRepeatByUpsteamName(nodeName, null)) {
            UpsteamNameAddReq addReq = new UpsteamNameAddReq();
            addReq.setDeployType(DeployTypeEnum.KUBERNETES.getKey());
            addReq.setUpsteamName(nodeName);
            upsteamNameService.add(addReq);
        }

        //保存对象
        this.save(dest);
        return true;
    }

    @Override
    public Boolean setRunningStatusDownByDevOpsK8sNotificationVo(SetRunningStatusDown orig) {

        Object id = Optional.ofNullable(orig).map(SetRunningStatusDown::getId).orElse(null);
        //不足5分钟不可弃用
        this.stopWarning(id + "");
        UpSteamNode target = findById(id);
        target.setRunningStatus(RunningStatusEnum.TERMINATED.getKey());

        //获取k8s master conf
        ServerMachineNodeSyncVo k8sConf;
        List<ServerMachineNodeSyncVo> list = serverMachineNodeService.findSyncKubernetesEndPoint();

        if (CollectionUtil.isNotEmpty(list)) {
            //这里只取第一个
            k8sConf = Optional.of(list).map(l -> l.get(0)).orElse(null);
        } else {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg() + " get k8s master conf empty");
        }


        //删除 k8s 上的 deployment 和 service
        Boolean removeExecResult = devOpsKubernetesService.removeDeploymentInfo(orig, k8sConf);

        if (!removeExecResult) {
            logger.error("removeDeploymentInfo removeExecResult = " + removeExecResult);
            throw new BusinessException(ErrorCodesEnum.SPRING_CLOUD_ERR.code(), ErrorCodesEnum.SPRING_CLOUD_ERR.msg() + " of removeDeploymentInfo");
        }
        logger.error("setRunningStatusDownByDevOpsK8sNotificationVo  target = ", JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
        this.update(target);
        return true;
    }

    @Override
    public Boolean isRepeatByDevOpsK8sNotificationVo(DevOpsK8sNotificationVo param) {

        String nodeName = Optional.ofNullable(param).map(DevOpsK8sNotificationVo::getNodeName).orElse("");
        ;
        String currentVersion = Optional.ofNullable(param).map(DevOpsK8sNotificationVo::getCurrentVersion).orElse("");
        ;
        String nodeNamespace = Optional.ofNullable(param).map(DevOpsK8sNotificationVo::getNodeNamespace).orElse("");
        ;

        if (StringUtils.isEmpty(nodeName) || StringUtils.isEmpty(currentVersion) || StringUtils.isEmpty(nodeNamespace)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " nodeName or currentVersion or nodeNamespace");
        }
        Integer count = baseDao.countByDevOpsK8sNotificationVo(nodeName, currentVersion, nodeNamespace);
        return count > 0;
    }

    @Override
    public void stopWarning(String id) {
        logger.info("stopWarning id = " + id);
        Integer count = baseDao.countStopTimeLessThan5Min(id);
        logger.info("stopWarning count = " + count);
        if (count > 0) {
            throw new BusinessException(ErrorCodesEnum.DEPRECATED_LESS_FIVE.code(), ErrorCodesEnum.DEPRECATED_LESS_FIVE.msg());
        }
    }
}