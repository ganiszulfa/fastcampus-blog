package com.fastcampus.blog.config;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

@Configuration
@EnableCaching
@AllArgsConstructor
public class CacheConfig implements CachingConfigurer {

    private RedisCacheManager redisCacheManager;

    @Override
    public CacheManager cacheManager() {
        final var cacheManager = new CompositeCacheManager(redisCacheManager);
        cacheManager.setFallbackToNoOpCache(true);
        return cacheManager;
    }
}
