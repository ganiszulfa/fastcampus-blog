package com.fastcampus.blog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "spring.redis")
@Component
public class RedisProperties {
    private String host;
    private String password;
    private String port;
}
