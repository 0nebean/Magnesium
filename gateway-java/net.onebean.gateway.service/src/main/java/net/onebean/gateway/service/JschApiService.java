package net.onebean.gateway.service;

import net.onebean.gateway.vo.ServerMachineNodeSyncVo;
import net.onebean.component.jsch.remote.ShellsCommand;

/**
 * jsch APi
 * @author Icc
 */
public interface JschApiService {
    /**
     * 获取shell命令行工具
     * @param serverMachineNodeSyncVo 服务节点
     * @return 命令行
     */
    ShellsCommand getShellsCommand(ServerMachineNodeSyncVo serverMachineNodeSyncVo);
}
