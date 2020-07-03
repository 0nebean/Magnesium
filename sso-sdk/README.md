[![GitHub release](https://img.shields.io/badge/release-1.0.0-28a745.svg)](https://github.com/0nebean/com.alibaba.druid-0nebean.custom/releases)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


Introduction
---
- 对spring-data-jedis和redisson的依赖和API的调用封装

Documentation
---
- Config
#### chemical-el框架使用Apollo作为注册中心 ,以下是需要的配置namespace
[public-conf.redis](https://github.com/0nebean/public.conf/blob/master/conf/public-conf.redis.properties)

- API
#### 对于 `jedis` 提供了部分API如下:
```java
    /**
     * 缓存值并设定时效
     * @param key 键 
     * @param value 值
     * @return boolean
     */
    boolean set(String key, String value,long time);  
    /**
     * 缓存值
     * @param key 键
     * @param value 值
     * @return boolean
     */
    boolean set(String key, String value);
    /**
     * 删除值
     * @param key 键
     * @return boolean
     */
    boolean del(String key);
    /**
     * 根据key获取缓存中的值  
     * @param key 键
     * @return String
     */
    String get(String key);  
      /**
       * 缓存list
       * @param key 键
       * @param list list
       * @return boolean
       */
    <T> boolean setList(String key ,List<T> list);  
      /**
       * 根据key获取缓存中的list
       * @param key 键
       * @param clz class
       * @return List<T>
       */
    <T> List<T> getList(String key,Class<T> clz);  
      /**
       * 在key对应list的头部添加字符串元素
       * @param key 键
       * @param obj 对象
       * @return long
       */
    long lpush(String key,Object obj);  
      /**
       * 在key对应list的尾部添加字符串元素
       * @param key 键
       * @param obj 对象
       * @return long
       */
    long rpush(String key,Object obj);  
      /**
       * 在key对应list上移除并返回list的最后一个元素
       * @param key 键
       * @return String
       */
    String lpop(String key);
    /**
     * 哈希添加
     * @param key 键 键
     * @param hashKey 哈希键
     * @param value 值 值
     */
    void hSet(String key, Object hashKey, Object value);
    /**
     * 哈希添加
     * @param key 键 键
     * @param values 值
     */
    void hSetAll(String key, Map<?,?>values);
    /**
     * 获取哈希
     * @param key 键 主键
     * @param hashKey 哈希键
     * @return obj
     */
    Object hGet(String key, Object hashKey);
    /**
     * 获取所有哈希键
     * @param key 键 主键
     * @return keys
     */
    Set<Object> hKeys(String key);
    /**
     * 删除哈希键
     * @param masterKey 主键
     * @param hashKey 哈希键
     */
    void hDel(String masterKey, Object... hashKey);
```


#### 对于 `redisson` 提供了部分API如下:

```java

    /**
     * 获取配置信息
     * @return Config
     */
    public Config getRedissonClientConf();

    /**`
     * 获取字符串对象
     * @param objectName key
     * @return <T>
     */
    public <T> RBucket<T> getRBucket(String objectName);

    /**
     * 获取Map对象
     * @param objectName key
     * @return <K, V> 
     */
    public <K, V> RMap<K, V> getRMap(String objectName);

    /**
     * 获取有序集合
     * @param objectName key
     * @return <V>
     */
    public <V> RSortedSet<V> getRSortedSet(String objectName);

    /**
     * 获取集合
     * @param objectName key
     * @return <V> RSet<V>
     */
    public <V> RSet<V> getRSet(String objectName);
    /**
     * 获取列表
     * @param objectName key
     * @return <V> RList<V>
     */
    public <V> RList<V> getRList(String objectName);

    /**
     * 获取队列
     * @param objectName key
     * @return <V> RQueue<V>
     */
    public <V> RQueue<V> getRQueue(String objectName);

    /**
     * 获取双端队列
     * @param objectName key
     * @return <V> RDeque<V>
     */
    public <V> RDeque<V> getRDeque(String objectName);

    /**
     * 获取锁
     * @param objectName key
     * @return RLock
     */
    public RLock getRLock(String objectName);

    /**
     * 获取读取锁
     * @param objectName key
     * @return RReadWriteLock
     */
    public RReadWriteLock getRWLock(String objectName);

    /**
     * 获取原子数
     * @param objectName key
     * @return RAtomicLong
     */
    public RAtomicLong getRAtomicLong(String objectName);
    /**
     * 获取记数锁
     * @param objectName key
     * @return RCountDownLatch
     */
    public RCountDownLatch getRCountDownLatch(String objectName);

    /**
     * 获取消息的Topic
     * @param objectName key
     * @return RTopic
     */
    public RTopic getRTopic(String objectName);
```

#### 当然你也可以实现你自己的分布式业务锁,像这样:
```java
/**
 * @author 0neBean
 * 分布式锁操作类
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
    public RLock getFairLock() {
        return redisson.getFairLock("FILE_GENERATION_LOCK");
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

```
