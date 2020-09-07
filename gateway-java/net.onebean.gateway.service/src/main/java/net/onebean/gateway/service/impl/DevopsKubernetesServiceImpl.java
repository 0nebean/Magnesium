package net.onebean.gateway.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.gateway.vo.SetRunningStatusDown;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.service.DevOpsKubernetesService;
import net.onebean.gateway.service.JschApiService;
import net.onebean.gateway.vo.DevOpsK8sNotificationVo;
import net.onebean.gateway.vo.K8sNodePortInfoVo;
import net.onebean.gateway.vo.ServerMachineNodeSyncVo;
import net.onebean.component.jsch.remote.ShellsCommand;
import net.onebean.core.error.BusinessException;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * k8s 交互APi
 *
 * @author Icc
 */
@Service
public class DevopsKubernetesServiceImpl implements DevOpsKubernetesService {

    private final static Logger logger = LoggerFactory.getLogger(DevopsKubernetesServiceImpl.class);
    @Autowired
    private JschApiService jschApiService;

    @Override
    public K8sNodePortInfoVo getPodNodePort(DevOpsK8sNotificationVo request, ServerMachineNodeSyncVo k8sConf) {

        K8sNodePortInfoVo resp = new K8sNodePortInfoVo();

        //获取 jsch 连接
        ShellsCommand shellsCommand = jschApiService.getShellsCommand(k8sConf);

        String nodeName = Optional.ofNullable(request).map(DevOpsK8sNotificationVo::getNodeName).orElse("");
        ;
        String currentVersion = Optional.ofNullable(request).map(DevOpsK8sNotificationVo::getCurrentVersion).orElse("");
        ;
        String nodeNamespace = Optional.ofNullable(request).map(DevOpsK8sNotificationVo::getNodeNamespace).orElse("");
        ;
        String k8sMasterIp = Optional.of(k8sConf).map(ServerMachineNodeSyncVo::getIpAddress).orElse("");
        ;

        nodeName = nodeName + "-deployment-" + currentVersion;
        nodeName = nodeName.replace(".", "-").replace("_", "-");

        //执行命令
        String respStr = shellsCommand.execReturnInfo("kubectl get service " + nodeName + " -n " + nodeNamespace + " -o json");
        //解析返回结果
        JSONObject jsonObject = JSONObject.parseObject(respStr);
        logger.debug("jsonObject = " + JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat));
        String nodePort = Optional.ofNullable(jsonObject).map(o -> o.getJSONObject("spec")).map(s -> s.getJSONArray("ports")).map(s -> s.getJSONObject(0)).map(s -> s.getString("nodePort")).orElse("");

        if (StringUtils.isEmpty(k8sMasterIp) || StringUtils.isEmpty(nodePort)) {
            throw new BusinessException(ErrorCodesEnum.JSCH_ERROR.code(), ErrorCodesEnum.JSCH_ERROR.msg() + " get nodePort from k8s master failure");
        }

        resp.setNodePort(nodePort);
        resp.setClusterip(k8sMasterIp);
        return resp;
    }

    @Override
    public Boolean removeDeploymentInfo(SetRunningStatusDown request, ServerMachineNodeSyncVo k8sConf) {
        //获取 jsch 连接
        ShellsCommand shellsCommand = jschApiService.getShellsCommand(k8sConf);

        String nodeName = Optional.ofNullable(request).map(SetRunningStatusDown::getNodeName).orElse("");
        ;
        String currentVersion = Optional.ofNullable(request).map(SetRunningStatusDown::getCurrentVersion).orElse("");
        ;
        String nodeNamespace = Optional.ofNullable(request).map(SetRunningStatusDown::getNodeNamespace).orElse("");
        ;

        nodeName = nodeName + "-deployment-" + currentVersion;
        nodeName = nodeName.replace(".", "-").replace("_", "-");

        //执行命令
        String respStr = shellsCommand.execReturnInfo("kubectl delete service " + nodeName + " -n " + nodeNamespace + " && kubectl delete deployment " + nodeName + " -n " + nodeNamespace);

        Integer count = StringUtils.containsCount(respStr, "deleted");
        return count != null && count == 2;
    }

}
