package net.onebean.gateway.service;


import org.redisson.api.*;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * redisson操作类
 */
@Service
public class RedissonService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取配置信息
     * @return Config
     */
    public Config getRedissonClientConf() {
        return redissonClient.getConfig();
    }
    /**
     * 获取字符串对象
     * @param objectName key
     * @param <T> 泛型类型
     * @return 结果
     */
    public <T> RBucket<T> getRBucket(String objectName) {
        RBucket<T> bucket = redissonClient.getBucket(objectName);
        return bucket;
    }

    /**
     * 获取Map对象
     * @param objectName key
     * @param <K> 键
     * @param <V> 值
     * @return 结果
     */
    public <K, V> RMap<K, V> getRMap(String objectName) {
        RMap<K, V> map = redissonClient.getMap(objectName);
        return map;
    }
    /**
     * 获取有序集合
     * @param objectName key
     * @param <V> 值
     * @return 结果
     */
    public <V> RSortedSet<V> getRSortedSet(String objectName) {
        RSortedSet<V> sortedSet = redissonClient.getSortedSet(objectName);
        return sortedSet;
    }
    /**
     * 获取集合
     * @param objectName key
     * @param <V> 泛型类型
     * @return 结果
     */
    public <V> RSet<V> getRSet(String objectName) {
        RSet<V> rSet = redissonClient.getSet(objectName);
        return rSet;
    }
    /**
     * 获取列表
     * @param objectName key
     * @param <V> 泛型类型
     * @return 结果
     */
    public <V> RList<V> getRList(String objectName) {
        RList<V> rList = redissonClient.getList(objectName);
        return rList;
    }
    /**
     * 获取队列
     * @param objectName key
     * @param <V> 泛型类型
     * @return 结果
     */
    public <V> RQueue<V> getRQueue(String objectName) {
        RQueue<V> rQueue = redissonClient.getQueue(objectName);
        return rQueue;
    }
    /**
     * 获取双端队列
     * @param objectName key
     * @param <V> 泛型
     * @return 结果
     */
    public <V> RDeque<V> getRDeque(String objectName) {
        RDeque<V> rDeque = redissonClient.getDeque(objectName);
        return rDeque;
    }


    /**
     * 获取锁
     * @param objectName key
     * @return RLock
     */
    public RLock getRLock(String objectName) {
        RLock rLock = redissonClient.getLock(objectName);
        return rLock;
    }

    /**
     * 获取读取锁
     * @param objectName key
     * @return RReadWriteLock
     */
    public RReadWriteLock getRWLock(String objectName) {
        RReadWriteLock rwlock = redissonClient.getReadWriteLock(objectName);
        return rwlock;
    }

    /**
     * 获取原子数
     * @param objectName key
     * @return RAtomicLong
     */
    public RAtomicLong getRAtomicLong(String objectName) {
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(objectName);
        return rAtomicLong;
    }

    /**
     * 获取记数锁
     * @param objectName key
     * @return RCountDownLatch
     */
    public RCountDownLatch getRCountDownLatch(String objectName) {
        RCountDownLatch rCountDownLatch = redissonClient.getCountDownLatch(objectName);
        return rCountDownLatch;
    }

    /**
     * 获取消息的Topic
     * @param objectName key
     * @return RTopic
     */
    public RTopic getRTopic(String objectName) {
        RTopic rTopic = redissonClient.getTopic(objectName);
        return rTopic;
    }
}
