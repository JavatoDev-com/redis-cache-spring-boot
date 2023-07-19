package com.javatodev.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisCacheSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisCacheSpringBootApplication.class, args);
    }

}
