package net.onebean.api.mngt.service;

/**
 * @author 0neBean
 */
public interface ManualUpdateServerNodeService {
    /**
     * 更新所有nginx 节点的 upsteam 信息
     */
    Boolean updateAllNginxUpSteamConf();
}
