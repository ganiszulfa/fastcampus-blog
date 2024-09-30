package com.fastcampus.blog.config;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
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

    @Bean
    public Config redissonConfig() {
        Config config = new Config();
        // todo: change to prop
        config.useSingleServer().setAddress("redis://localhost:6379");
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
