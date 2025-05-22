package com.jktechproject.documentmanagement.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisTokenSErvice {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisTokenSErvice(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeToken(String token, long timeoutInMillis) {
        redisTemplate.opsForValue().set(token, "valid", timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenValid(String token) {
        return redisTemplate.hasKey(token);
    }

    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
}
