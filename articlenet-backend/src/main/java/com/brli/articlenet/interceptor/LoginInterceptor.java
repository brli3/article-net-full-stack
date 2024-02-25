package com.brli.articlenet.interceptor;

import com.brli.articlenet.utils.JwtUtil;
import com.brli.articlenet.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // verify JWT token
        String token = request.getHeader("Authorization");
        try {
            // Get the same token from Redis
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            String redisToken = ops.get(token);
            if (Objects.isNull(redisToken)) {
                // Token is already invalid
                log.error("Token is not valid anymore");
                throw new RuntimeException("Token invalid");
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // save user data in ThreadLocal and access it in controllers
            // otherwise, controllers have to parse token sent in as args
            ThreadLocalUtil.set(claims);
            return true; // user logged in and token is valid, pass
        } catch (Exception e) {
            // http response code 401 unauthorised
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // clear ThreadLocal to avoid memory leakage
        ThreadLocalUtil.remove();
    }
}
