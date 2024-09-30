package com.fastcampus.blog.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Bucket4jConfig {

    private final ProxyManager<String> proxyManager;

    public Bucket4jConfig(ProxyManager<String> proxyManager) {
        this.proxyManager = proxyManager;
    }

    public Bucket loginBucket(String username) {
        Refill refill = Refill.intervally(3, Duration.ofMinutes(1));
        Bandwidth bandwidth = Bandwidth.classic(3, refill);
        BucketConfiguration configuration = BucketConfiguration.builder()
                .addLimit(bandwidth).build();
        return proxyManager.builder().build(username, () -> configuration);
    }

    public Bucket loginBucket() {
        Refill refill = Refill.intervally(3, Duration.ofMinutes(1));
        Bandwidth bandwidth = Bandwidth.classic(3, refill);
        return Bucket.builder().addLimit(bandwidth).build();
    }

    @Bean
    public Bucket chatGptRequestBucket() {
        Refill refillByMinute = Refill.intervally(3, Duration.ofMinutes(1));
        Refill refillByDay = Refill.intervally(200, Duration.ofDays(1));
        Bandwidth bandwidthByMinute = Bandwidth.classic(3, refillByMinute);
        Bandwidth bandwidthByDay = Bandwidth.classic(200, refillByDay);
        return Bucket.builder().addLimit(bandwidthByDay).addLimit(bandwidthByMinute).build();
    }
}
