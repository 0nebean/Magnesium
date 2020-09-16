package net.onebean.gateway.service.impl;

import net.onebean.gateway.Runnable.NginxReloadRunner;
import net.onebean.gateway.Runnable.RollBackRemoteNginxConfRunner;
import net.onebean.gateway.Runnable.UpdateSingleRemoteNginxConfRunner;
import net.onebean.gateway.common.ConfPathHelper;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.enumModel.RunnerExecStatusEnum;
import net.onebean.gateway.service.JschApiService;
import net.onebean.gateway.service.ServerMachineNodeService;
import net.onebean.gateway.service.UpgradeNginxConfService;
import net.onebean.gateway.vo.ServerMachineNodeSyncVo;
import net.onebean.component.jsch.remote.ShellsCommand;
import net.onebean.core.error.BusinessException;
import net.onebean.util.CollectionUtil;
import net.onebean.util.DateUtils;
import net.onebean.util.IOUtils;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Service
public class UpgradeNginxConfServiceImpl implements UpgradeNginxConfService {

    private final static Logger logger = LoggerFactory.getLogger(UpgradeNginxConfServiceImpl.class);
    private final static Boolean IS_SYNC_UPDATE_NGINX = false;

    @Autowired
    private JschApiService jschApiService;
    @Autowired
    private ServerMachineNodeService serverMachineNodeService;



    // 正在进行中的reload、准备reload状态，key为host
    public final static Map<String, Boolean> reloadStatus = new ConcurrentHashMap<>();


    public String backupRemoteNginxConf(ShellsCommand shellsCommand, String backupPath, String uagConfPath) {
        /*执行以下命令 生成备份目录 拷贝远程base目录下conf到备份目录*/
        logger.info("Backup remote nginx conf start. Remote nginx server is {}", shellsCommand.getConfig().getHost());
        try {
            String destinationPath = backupPath + "/conf.d";
            shellsCommand.exec("mkdir -p " + destinationPath + ";cp -rf " + uagConfPath + "/conf.d " + backupPath);
            destinationPath = backupPath + "/front";
            shellsCommand.exec("mkdir -p " + destinationPath + ";cp -rf " + uagConfPath + "/front " + backupPath);
        } catch (BusinessException e) { // 此备份异常暂时只捕获打印日志
            logger.warn("backup remote nginx conf exception. Remote nginx server is " + shellsCommand.getConfig().getHost(), e);
        }
        logger.info("Backup remote nginx conf finish. Remote nginx server is {}", shellsCommand.getConfig().getHost());
        return backupPath;
    }

    public void deleteRemoteNginxConf(ShellsCommand shellsCommand, List<String> removeEntities,String deletePath, String uagConfPath) {
        /*该删除操作只是按照时间创建temp文件夹  将文件归置到该目录下*/
        if (CollectionUtil.isEmpty(removeEntities)) {
            logger.warn("Delete remote nginx conf method ,the param removeEntities is none.");
            return;
        }
        int i = 0;
        StringBuilder removePaths = new StringBuilder();
        Iterator<String> iterator = removeEntities.iterator();
        if (iterator.hasNext()) {
            String iterPath = "";
            while (iterator.hasNext()) {
                String tmpBak = deletePath + "/" + i;
                iterPath = uagConfPath+"/"+iterator.next();
                removePaths.append(" ; test -e ").append(iterPath).append(" &&  mkdir -p " + tmpBak).append(" && mv -f ").append(iterPath).append(" ").append(tmpBak).append(" || echo 'file not exit ,do nothing !' ");
                i++;
            }
        }
        try {
            shellsCommand.exec(removePaths.toString().replaceFirst(" ; ",""));
        } catch (BusinessException e) { // 此删除异常暂时只捕获打印日志
            logger.warn("delete remote nginx conf exception. Remote nginx server is " + shellsCommand.getConfig().getHost() + ",command is " + removePaths, e);
        }
    }

    public void reloadNginx(ShellsCommand shellsCommand, String backupPath, boolean isSync) throws BusinessException {
        /*reload nginx 向上抛出业务异常*/
        reloadNginxForRollBack(shellsCommand, isSync);
        try {
            /*reload成功后，把刚才备份的文件夹删除*/
            shellsCommand.exec("mv " + backupPath + " " + backupPath+"-bak");
            shellsCommand.exec("mv " + backupPath + "-bak " + ConfPathHelper.getRemoteDeletePath());
        } catch (BusinessException e) { // 此删除异常暂时只捕获打印日志
            logger.warn("remove remote nginx conf exception. Remote nginx server is " + shellsCommand.getConfig().getHost(), e);
        }
    }

    public void reloadNginxForRollBack(ShellsCommand shellsCommand, boolean isSync) throws BusinessException {
        String host = shellsCommand.getConfig().getHost();

        shellsCommand.exec("/usr/local/openresty/nginx/sbin/nginx -t");

        /*当需要同步reload时候*/
        if (isSync) {
            shellsCommand.exec("/usr/local/openresty/nginx/sbin/nginx -s reload");
            return;
        }

        logger.info("check nginx succeeded. Remote nginx server is {}", host);

        /*如果reloadStatus中对应的host中存在准备reload状态时候*/
        if (reloadStatus.get(host) != null && reloadStatus.get(host)) {
            /*此时直接返回，因为不需要再次reload的了*/
            return;
        }
        reloadStatus.put(host, true);
        /*使用线程的方式进行nginx的reload，主要目的是节省时间，提高反应速度*/
        TaskExecutors.UAGTHREADPOOL.execute(new NginxReloadRunner(shellsCommand));
    }


    public void rollBackAllRemoteNginxConf(List<String> coverFiles, List<String> removeFiles, List<ServerMachineNodeSyncVo> nginxInfos, String deletePath, String backupPath) throws InterruptedException {
        /*用线程的方式回滚所有nginx机器配置*/
        CountDownLatch rollBackLatch = new CountDownLatch(nginxInfos.size());
        Set<String> rollBackFlagSet = new HashSet<>();
        for (ServerMachineNodeSyncVo nginxInfo : nginxInfos) {
            RollBackRemoteNginxConfRunner runner = new RollBackRemoteNginxConfRunner(rollBackLatch, nginxInfo, coverFiles, removeFiles,deletePath, backupPath, rollBackFlagSet);
            TaskExecutors.UAGTHREADPOOL.execute(runner);
        }
        rollBackLatch.await();

        if (rollBackFlagSet.contains(RunnerExecStatusEnum.FAILURE.getKey())) {
            throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_UN_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_UN_ROLL_BACK_ERROR.msg());
        }
    }

    public void rollBackRemoteNginxConf(ServerMachineNodeSyncVo nginxInfo, List<String> coverEntities, List<String> removeEntities, String deletePath, String backupPath) {
        ShellsCommand shellsCommand = jschApiService.getShellsCommand(nginxInfo);
        logger.info("Roll back remote nginx conf start. Remote nginx server is {}", shellsCommand.getConfig().getHost());
        //将涉及到更新的目录删除，然后将备份目录中的相关目录覆盖过来,然后再将删除文件全部还原回来
        String uagConfPath = ConfPathHelper.getLocalBasePath();
        if (StringUtils.isEmpty(uagConfPath)) {
            throw new BusinessException(ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.code(), ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.msg() + " uagConfPath is empty");
        }
        String removeTmpPath = ConfPathHelper.getRemoteDeletePath() + "/" + DateUtils.getDetailTime();
        StringBuilder removeCommand = new StringBuilder();
        StringBuilder copyCommand = new StringBuilder();
        int i = 0;

        if (null != coverEntities) {
            for (String coverEntity : coverEntities) {
                if (coverEntity.contains("*")) {
                    throw new BusinessException(ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.code(), ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.msg());
                }
                logger.info("uagConfPath = " + uagConfPath);
                logger.info("coverEntity = " + coverEntity);
                String subRemoveTmpPath = removeTmpPath + "/" + i++;
                removeCommand.append("mkdir -p ").append(subRemoveTmpPath).append(";").append("mv ").append(uagConfPath).append("/").append(coverEntity).append(" ").append(subRemoveTmpPath).append(";");
                copyCommand.append("mkdir -p ").append(uagConfPath).append(IOUtils.getPathOfFile(coverEntity)).append(";");
                copyCommand.append("cp -rf ").append(backupPath).append("/").append(coverEntity).append(" ").append(uagConfPath).append("/").append(coverEntity).append(";");
            }
        }
        if (null != removeEntities) {
            i = 0;
            for (String removeEntity : removeEntities) {
                if (removeEntity.contains("*")) {
                    throw new BusinessException(ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.code(), ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.msg());
                }
                logger.info("uagConfPath = " + uagConfPath);
                logger.info("coverEntity = " + removeEntity);
                copyCommand.append("mkdir -p ").append(uagConfPath).append(IOUtils.getPathOfFile(removeEntity)).append(";");
                copyCommand.append("mv ").append(deletePath).append("/").append(i++).append("/*").append(" ").append(uagConfPath).append(IOUtils.getPathOfFile(removeEntity)).append(";");
            }
        }
        String finalCommand = removeCommand.append(copyCommand.toString()).toString().replace("\\", "/");
        try {
            shellsCommand.exec(finalCommand);
        } catch (BusinessException e) { // 此回滚异常暂时只捕获打印日志
            logger.warn("Roll back remote nginx conf exception. Remote nginx server is " + shellsCommand.getConfig().getHost() + ",command is " + finalCommand, e);
        }
        try {
            reloadNginxForRollBack(shellsCommand, IS_SYNC_UPDATE_NGINX);
            logger.info("Roll back remote nginx conf finish. Remote nginx server is {}", shellsCommand.getConfig().getHost());
        } catch (BusinessException e) {
            throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.msg());
        }
    }


    @Override
    public void updateSingleRemoteNginxConf(ServerMachineNodeSyncVo nginxInfo, List<String> coverEntities, List<String> removeEntities,String deletePath, String backupPath, boolean isSync) {
        /*获取shell 命令行*/
        ShellsCommand remoteNginxShells = jschApiService.getShellsCommand(nginxInfo);
        logger.info("Update single remote nginx server , nginx ip is {}", remoteNginxShells.getConfig().getHost());
        String remoteBackupPath;
        String uagConfPath;
        try {
            remoteNginxShells.exec("mkdir -p /tmp/uag/backup && mkdir -p /tmp/uag/delete && mkdir -p /opt/tar");
            uagConfPath = ConfPathHelper.getLocalBasePath();
            if (StringUtils.isEmpty(uagConfPath)) {
                throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.msg() + " uagConfPath is empty");
            }
            /*先备份远程nginx配置*/
            remoteBackupPath = backupRemoteNginxConf(remoteNginxShells, backupPath, uagConfPath);
            /*删除必要的条目*/
            if (CollectionUtil.isNotEmpty(removeEntities)) {
                deleteRemoteNginxConf(remoteNginxShells, removeEntities,deletePath, uagConfPath);
            }
            /*remote push  将本地打包文件推送到远程nginx上*/
            logger.info("Pushing local files to nginx server , nginx ip is {}", remoteNginxShells.getConfig().getHost());
            remoteNginxShells.scp(ConfPathHelper.getLocalTarFilePath(), uagConfPath);
            /*使用最新的配置进行覆盖*/
            remoteNginxShells.exec("tar xf " + uagConfPath + "/config.tar.gz -C " + uagConfPath);
            /*校验nginx和重新reload*/
            reloadNginx(remoteNginxShells, remoteBackupPath, isSync);
        } catch (Exception e) {
            logger.error("updateSingleRemoteNginxConf get error = ", e);
            throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.msg() + "update single remote nginx conf error." + e.getMessage());
        }
    }


    @Override
    public void updateAllRemoteNginxConf(List<String> coverFiles, List<String> removeFiles, boolean isSync) {
        try {
            /*获取所有需要更新的nginx服务器*/
            List<ServerMachineNodeSyncVo> nginxInfos = findAllNginxSyncNode();
            /*逻辑上至少有一台nginx 程序才成立,否则报错*/
            if (CollectionUtil.isEmpty(nginxInfos)) {
                throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg());
            }

            /*所有远程nginx机器上备份的目录*/
            String backupPath = ConfPathHelper.getRemoteBackupPath();
            String deletePath = ConfPathHelper.getRemoteDeletePath() + "/" + DateUtils.getDetailTime()+"-del";
            /*用线程的方式更新每一台机器的nginx*/
            CountDownLatch updateLatch = new CountDownLatch(nginxInfos.size());
            Set<String> updateFlagSet = new HashSet<>();
            for (ServerMachineNodeSyncVo nginxInfo : nginxInfos) {
                UpdateSingleRemoteNginxConfRunner runner = new UpdateSingleRemoteNginxConfRunner(updateLatch, nginxInfo, coverFiles, removeFiles, updateFlagSet,deletePath, backupPath, isSync);
                TaskExecutors.UAGTHREADPOOL.execute(runner);
            }
            updateLatch.await();

            /*如果出现失败的情况 进行回滚*/
            if (updateFlagSet.contains(RunnerExecStatusEnum.FAILURE.getKey())) {
                rollBackAllRemoteNginxConf(coverFiles, removeFiles, nginxInfos,deletePath, backupPath);
                throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_UN_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_UN_ROLL_BACK_ERROR.msg());
            }
        } catch (BusinessException e) {
            /*捕获业务异常 主要用于捕获 回滚失败异常*/
            throw new BusinessException(e.getCode(), e.getMsg());
        } catch (Exception e) {
            /*更新失败 回滚成功异常*/
            throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.msg());
        } finally {
            /*删除本地conf 文件*/
            File file = new File(ConfPathHelper.getLocalTarFilePath());
            if (!file.exists()) {
                IOUtils.deleteDir(ConfPathHelper.getLocalTarFilePath());
            }
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ServerMachineNodeSyncVo> findAllNginxSyncNode() {
        List<ServerMachineNodeSyncVo> list = serverMachineNodeService.findSyncOpenrestyEndPoint();
        if (CollectionUtil.isNotEmpty(list)) {
            return list;
        } else {
            logger.warn("findAllNginxSyncNode findSyncServerMachineNode res is empty");
            return Collections.EMPTY_LIST;
        }
    }
}
