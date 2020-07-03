package net.onebean.api.mngt.service;

import net.onebean.api.mngt.vo.ServerMachineNodeSyncVo;
import net.onebean.component.jsch.remote.ShellsCommand;

/**
 * jsch APi
 * @author 0neBean
 */
public interface JschApiService {
    /**
     * 获取shell命令行工具
     * @param serverMachineNodeSyncVo 服务节点
     * @return 命令行
     */
    ShellsCommand getShellsCommand(ServerMachineNodeSyncVo serverMachineNodeSyncVo);
}
