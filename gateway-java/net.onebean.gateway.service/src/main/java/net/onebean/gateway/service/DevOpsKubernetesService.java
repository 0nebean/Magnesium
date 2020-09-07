package net.onebean.gateway.service;


import net.onebean.gateway.vo.SetRunningStatusDown;
import net.onebean.gateway.vo.DevOpsK8sNotificationVo;
import net.onebean.gateway.vo.K8sNodePortInfoVo;
import net.onebean.gateway.vo.ServerMachineNodeSyncVo;

/**
 * k8s 交互APi
 *
 * @author Icc
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
