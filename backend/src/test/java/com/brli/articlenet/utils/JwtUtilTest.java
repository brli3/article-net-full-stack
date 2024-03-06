package com.brli.articlenet.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brli.articlenet.model.User;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private static final Integer id = 1;
    private static final String username = "Tom";

    private static String exampleToken;

    @Test
    public void testGen() {
        // generate jwt token
        // 1. claims
        // 2. expiry time
        // 3. key for encoding algorithm
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        String token = JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 12 * 60 * 60))
                .sign(Algorithm.HMAC256("brli3"));
        exampleToken = token;
        assertNotNull(token);
    }

    @Test
    public void testParse() {
        testGen();
        // decoding fails if
        // 1. inconsistent key used for verification
        // 2. token has been modified
        // 3. token expired
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("brli3")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(exampleToken);
        Map<String, Claim> claims = decodedJWT.getClaims();
        Map<String, Object> userMap = claims.get("user").asMap();
        assertEquals(id, (int) userMap.get("id"));
        assertEquals(username, userMap.get("username"));
    }

}
