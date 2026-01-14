package com.pwnned.adapter.output.redis;

import com.pwnned.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UserRedisAdapter {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheUser(User user) {
        String key = "user:" + user.getUserId();
        redisTemplate.opsForValue().set(key, user, 1,  TimeUnit.HOURS);
    }

    public Optional<User> getCachedUser(Long userId) {
        String key = "user:" + userId;
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject instanceof User) {
            return Optional.of((User) cachedObject);
        }
        return Optional.empty();
    }

    public void cacheUsersByType(String userType, List<User> users) {
        String key = "users:type:" + userType.toUpperCase();
        redisTemplate.opsForValue().set(key, users, 1, TimeUnit.HOURS);
    }

    @SuppressWarnings("unchecked")
    public Optional<List<User>> getCachedUsersByType(String userType) {
        String key = "users:type:" + userType.toUpperCase();
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject instanceof List) {
            return Optional.of((List<User>) cachedObject);
        }
        return Optional.empty();
    }

    public void invalidateCacheForUser(Long userId) {
        String key = "user:" + userId;
        redisTemplate.delete(key);
    }

    public void invalidateCacheForUsersByType(String userType) {
        String key = "users:type:" + userType.toUpperCase();
        redisTemplate.delete(key);
    }

    public void invalidateAllUsersCache() {
        Set<String> typeKeys = redisTemplate.keys("users:type:*");
        if (typeKeys != null && !typeKeys.isEmpty()) {
            redisTemplate.delete(typeKeys);
        }

        Set<String> userKeys = redisTemplate.keys("user:*");
        if (userKeys != null && !userKeys.isEmpty()) {
            redisTemplate.delete(userKeys);
        }
    }
}
