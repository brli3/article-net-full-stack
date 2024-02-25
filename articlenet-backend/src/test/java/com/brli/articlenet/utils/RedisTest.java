package com.brli.articlenet.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest // initialise IOC before unit test
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testSet() {
        // save a key-value pair in Redis - StringRedisTemplate
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("username", "redis_user");
        ops.set("user:id", "2", 15, TimeUnit.SECONDS);
    }

    @Test
    public void testGet() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String username = ops.get("username");
        System.out.println("username from redis test: " + username);
    }
}
