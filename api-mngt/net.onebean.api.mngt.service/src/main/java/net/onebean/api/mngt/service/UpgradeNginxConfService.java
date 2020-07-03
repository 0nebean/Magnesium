package net.onebean.api.mngt.service;

import net.onebean.component.jsch.remote.ShellsCommand;
import net.onebean.api.mngt.vo.ServerMachineNodeSyncVo;

import java.util.List;

public interface UpgradeNginxConfService {
    /**
     * 备份，不包括log目录
     * @param shellsCommand shell命令行
     */
    String backupRemoteNginxConf(ShellsCommand shellsCommand, String backupPath, String uagConfPath);
    /**
     * 删除远程nginx服务器上的配置
     * @param shellsCommand shell命令行
     * @param removeEntities 需要删除的条目，相对路径，例如 /conf.d/tenant/xxxx/tenant.conf
     */
    void deleteRemoteNginxConf(ShellsCommand shellsCommand, List<String> removeEntities, String uagConfPath);
    /**
     * 普通情况下的reload
     * @param shellsCommand shell命令行
     * @param backupPath 备份路径
     * @param isSync 是否同步操作
     */
    void reloadNginx(ShellsCommand shellsCommand, String backupPath, boolean isSync) ;
    /**
     * 回滚用的reload
     * @param shellsCommand shell命令行
     * @param isSync 是否同步操作
     */
    void reloadNginxForRollBack(ShellsCommand shellsCommand, boolean isSync);
    /**
     * 回滚单个 nginx 配置
     * @param nginxInfo 单个nginx配置
     * @param coverEntities 需要覆盖的条目
     * @param removeEntities 需要删除的条目，相对路径，例如 /conf.d/tenant/xxxx/tenant.conf
     * @param backupPath 删除的条目备份路径
     */
    void rollBackRemoteNginxConf(ServerMachineNodeSyncVo nginxInfo, List<String> coverEntities, List<String> removeEntities, String backupPath);
    /**
     * 回滚所有机器上的nginx配置
     * @param coverFiles 需要覆盖的文件
     * @param removeFiles 需要删除的文件
     * @param nginxInfos nginx配置list
     * @throws InterruptedException
     */
    void rollBackAllRemoteNginxConf(List<String> coverFiles, List<String> removeFiles, List<ServerMachineNodeSyncVo> nginxInfos, String backupPath) throws InterruptedException;
    /**
     *  更新单台nginx配置文件
     * @param coverEntities 需要覆盖的条目
     * @param removeEntities 需要删除的条目，相对路径，例如 /conf.d/tenant/xxxx/tenant.conf
     * @param backupPath 服务器上的备份路径
     * @param isSync 是否同步
     */
    void updateSingleRemoteNginxConf(ServerMachineNodeSyncVo nginxInfo, List<String> coverEntities, List<String> removeEntities, String backupPath, boolean isSync);
    /**
     *  更新所有nginx服务
     * @param coverFiles 需要覆盖的文件
     * @param removeFiles 需要删除的文件
     * @param isSync 是否同步
     */
    void updateAllRemoteNginxConf(List<String> coverFiles, List<String> removeFiles, boolean isSync);
    /**
     * 查找所有的nginx方法
     * @return list
     */
    List<ServerMachineNodeSyncVo>findAllNginxSyncNode();
}
