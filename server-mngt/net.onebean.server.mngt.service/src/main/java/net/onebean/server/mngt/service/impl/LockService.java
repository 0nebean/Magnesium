package net.onebean.server.mngt.service.impl;


import net.onebean.core.error.BusinessException;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 0neBean
 *         分布式锁操作类
 */
@Service
public class LockService {

    @Autowired
    private RedissonClient redisson;

    /**
     * 获取公平锁
     *
     * @return
     */
    public RLock getUpdateNginxLock() {
        return redisson.getFairLock("UPDATE_NGINX_LOCK");
    }


    /**
     * 锁定
     *
     * @param lock
     * @throws InterruptedException
     */
    public void lock(RLock lock) throws InterruptedException {
        boolean res = lock.tryLock(180, 120, TimeUnit.SECONDS);
        if (!res) {
            throw new BusinessException(ErrorCodesEnum.GET_REDIS_LOCK_ERR.code(), ErrorCodesEnum.GET_REDIS_LOCK_ERR.msg());
        }
    }

    /**
     * 解锁
     *
     * @param lock
     */
    public void unLock(RLock lock) {
        if (lock != null) {
            lock.unlock();
        }
    }
}
