package net.onebean.gateway.service;

/**
 * @author Icc
 */
public interface ManualUpdateServerNodeService {
    /**
     * 更新所有nginx 节点的 upsteam 信息
     */
    Boolean updateAllNginxUpSteamConf();
}
