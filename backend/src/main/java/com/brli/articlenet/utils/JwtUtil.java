package com.brli.articlenet.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component // for @Value to work on static variables
public class JwtUtil {
    private static String key;
    private static Integer jwtExpiryTimeInHours;

    // Setter injection required for @Value to read properties
    @Value("${jwt.key}")
    public void setKey(String key) {
        JwtUtil.key = key;
    }

    @Value("${jwt.expiry-time}")
    public void setJwtExpiryTimeInHours(Integer jwtExpiryTimeInHours) {
        JwtUtil.jwtExpiryTimeInHours = jwtExpiryTimeInHours;
    }

    /**
     * Generate JWT token
     * @param claims encapsulated user login data
     * @return JWT token in string
     */
    public static String genToken(Map<String, Object> claims) {
        int expiryTime = Objects.isNull(jwtExpiryTimeInHours) ? 1 : jwtExpiryTimeInHours;
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * expiryTime))
                .sign(Algorithm.HMAC256(key));
    }

    /**
     * Return data from JWT token
     * @param token JWT token
     * @return typically user login data
     */
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(key))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
