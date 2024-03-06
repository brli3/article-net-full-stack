package com.brli.articlenet.controller;

import com.brli.articlenet.model.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController()
@RequestMapping("/hello")
public class HelloController {
    private static int count = 1;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping
    public Result<String> hello() {
        return Result.success("Hello " + count++);
    }

    @GetMapping("/redis-status")
    public Result<String> getRedisStatus(HttpServletResponse response) {
        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set("testKey", "testValue");
            String value = ops.get("testKey");
            return Result.success("Connection successful: " + value);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return Result.error("Redis connection failed");
        }
    }

}
