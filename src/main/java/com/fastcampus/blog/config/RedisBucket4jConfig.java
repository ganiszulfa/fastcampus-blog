package com.fastcampus.blog.config;

import com.fastcampus.blog.properties.RedisProperties;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

@Configuration
public class RedisBucket4jConfig {

    private final String cacheName = "bucket4j-rate-limits";

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public Config redissonConfig() {
        Config config = new Config();
        String addr = "%s:%s".formatted(redisProperties.getHost(), redisProperties.getPort());
        addr = "redis://%s".formatted(addr);
        config.useSingleServer().setAddress(addr);
        if (!redisProperties.getPassword().isBlank()) {
            config.useSingleServer().setPassword(redisProperties.getPassword());
        }
        config.useSingleServer()
                .setSubscriptionConnectionMinimumIdleSize(1)
                .setSubscriptionConnectionPoolSize(2)
                .setConnectionPoolSize(8)
                .setConnectionMinimumIdleSize(2);
        return config;
    }

    @Bean
    public CacheManager bucket4jCacheManager(Config redissonConfig) {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        cacheManager.createCache(cacheName,
                RedissonConfiguration.fromConfig(redissonConfig));
        return cacheManager;
    }

    @Bean
    public ProxyManager<String> proxyManager(CacheManager manager) {
        return new JCacheProxyManager<>(manager.getCache(cacheName));
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
