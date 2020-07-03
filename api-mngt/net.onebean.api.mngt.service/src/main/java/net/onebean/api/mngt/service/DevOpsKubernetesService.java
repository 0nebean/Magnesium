package net.onebean.api.mngt.service;


import net.onebean.api.mngt.vo.DevOpsK8sNotificationVo;
import net.onebean.api.mngt.vo.K8sNodePortInfoVo;
import net.onebean.api.mngt.vo.ServerMachineNodeSyncVo;
import net.onebean.api.mngt.api.model.SetRunningStatusDown;

/**
 * k8s 交互APi
 *
 * @author 0neBean
 */
public interface DevOpsKubernetesService {

    /**
     * 获取pod的nodeInfo信息
     *
     * @param request 参数
     * @param k8sConf 配置
     * @return vo
     */
    K8sNodePortInfoVo getPodNodePort(DevOpsK8sNotificationVo request, ServerMachineNodeSyncVo k8sConf);

    /**
     * 移除pod的deployment和service信息
     *
     * @param request 参数
     * @param k8sConf 配置
     * @return bool
     */
    Boolean removeDeploymentInfo(SetRunningStatusDown request, ServerMachineNodeSyncVo k8sConf);
}
