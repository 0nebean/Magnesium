package net.onebean.gateway.Runnable;

import net.onebean.gateway.service.UpgradeNginxConfService;
import net.onebean.gateway.service.impl.UpgradeNginxConfServiceImpl;
import net.onebean.gateway.vo.ServerMachineNodeSyncVo;
import net.onebean.component.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author Icc
 * 还原单台 nginx Runnable
 */
public class RollBackRemoteNginxConfRunner implements Runnable {

    @Autowired
    private UpgradeNginxConfService nginxConfUpDateService;

    private CountDownLatch latch;
    private ServerMachineNodeSyncVo serverMachineNodeSyncVo;
    private List<String> coverEntities;
    private List<String> removeEntities;
    private String remoteBackupPath;
    private Set<String> flagSet;


    public RollBackRemoteNginxConfRunner(CountDownLatch latch, ServerMachineNodeSyncVo nginxInfo, List<String> coverEntities, List<String> removeEntities, String remoteBackupPath, Set<String> flagSet) {
        super();
        this.latch = latch;
        this.serverMachineNodeSyncVo = nginxInfo;
        this.coverEntities = coverEntities;
        this.removeEntities = removeEntities;
        this.remoteBackupPath = remoteBackupPath;
        this.flagSet = flagSet;
    }

    @Override
    public void run() {
        try {
            nginxConfUpDateService = SpringUtil.getBean(UpgradeNginxConfServiceImpl.class);
            nginxConfUpDateService.rollBackRemoteNginxConf(serverMachineNodeSyncVo, coverEntities, removeEntities, remoteBackupPath);
            flagSet.add("1");
        } catch (Exception e) {
            flagSet.add("0");
        } finally {
            latch.countDown();
        }
    }

}