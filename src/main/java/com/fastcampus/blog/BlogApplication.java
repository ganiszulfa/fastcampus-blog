package com.fastcampus.blog;

import com.fastcampus.blog.properties.RedisProperties;
import com.fastcampus.blog.properties.SecretProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		SecretProperties.class,
		RedisProperties.class
})
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
