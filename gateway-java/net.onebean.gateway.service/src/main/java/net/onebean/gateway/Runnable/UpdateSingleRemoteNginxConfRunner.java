package net.onebean.gateway.Runnable;

import net.onebean.gateway.enumModel.RunnerExecStatusEnum;
import net.onebean.gateway.service.UpgradeNginxConfService;
import net.onebean.gateway.service.impl.UpgradeNginxConfServiceImpl;
import net.onebean.gateway.vo.ServerMachineNodeSyncVo;
import net.onebean.component.SpringUtil;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class UpdateSingleRemoteNginxConfRunner implements Runnable {

    private UpgradeNginxConfService upgradeNginxConfService;

    private CountDownLatch latch;
    private ServerMachineNodeSyncVo nginxInfo;
    private List<String> coverEntities;
    private List<String> removeEntities;
    private Set<String> flagSet;
    private String unifiedRemoteBackupPath;
    private boolean isSync;

    public UpdateSingleRemoteNginxConfRunner(CountDownLatch latch, ServerMachineNodeSyncVo nginxInfo, List<String> coverEntities, List<String> removeEntities, Set<String> flagSet, String unifiedRemoteBackupPath, boolean isSync) {
        super();
        this.latch = latch;
        this.nginxInfo = nginxInfo;
        this.coverEntities = coverEntities;
        this.removeEntities = removeEntities;
        this.flagSet = flagSet;
        this.unifiedRemoteBackupPath = unifiedRemoteBackupPath;
        this.isSync = isSync;
    }

    @Override
    public void run() {
        try {
            upgradeNginxConfService = SpringUtil.getBean(UpgradeNginxConfServiceImpl.class);
            upgradeNginxConfService.updateSingleRemoteNginxConf(nginxInfo, coverEntities, removeEntities, unifiedRemoteBackupPath, isSync);
            flagSet.add(RunnerExecStatusEnum.SUCCESSFUL.getKey());
        } catch (Exception e) {
            flagSet.add(RunnerExecStatusEnum.FAILURE.getKey());
        } finally {
            latch.countDown();
        }
    }
}