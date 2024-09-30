package com.fastcampus.blog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@PropertySource("classpath:secret.properties")
@ConfigurationProperties(prefix = "blog")
@Component
public class SecretProperties {
    private String userUsername;
    private String userPassword;
    private String jwtIss;
    private String jwtSecretKey;

    private String chatGptUrl;
    private String chatGptKey;
}
