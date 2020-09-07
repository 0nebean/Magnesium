package net.onebean.gateway.component;

import com.alibaba.fastjson.JSONObject;
import net.onebean.util.PropUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * redisson 配置类
 * @author Icc
 */
@Configuration
public class RedissonConfig {

    private final static String CONFIG_LEY = "redisson.config.json";



    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() throws IOException {
        String jsonConfig = PropUtil.getInstance().getConfig(CONFIG_LEY,PropUtil.PUBLIC_CONF_REDIS);
        JSONObject o = JSONObject.parseObject(jsonConfig);
        Config config = Config.fromJSON(o.toJSONString());
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        return new RedissonSpringCacheManager(redissonClient, config);
    }

}
