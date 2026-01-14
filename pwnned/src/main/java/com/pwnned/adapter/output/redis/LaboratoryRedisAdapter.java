package com.pwnned.adapter.output.redis;

import com.pwnned.domain.model.Laboratory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LaboratoryRedisAdapter {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheLaboratory(Laboratory laboratory) {
        String key = "laboratory:" + laboratory.getLabId();
        redisTemplate.opsForValue().set(key, laboratory, 1, TimeUnit.HOURS);
    }

    public Optional<Laboratory> getCachedLaboratory(Long laboratoryId) {
        String key = "laboratory:" + laboratoryId;
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject instanceof Laboratory) {
            return Optional.of((Laboratory) cachedObject);
        }
        return Optional.empty();
    }

    public void cacheLaboratoryByType(String laboratoryType, List<Laboratory> laboratories) {
        String key = "laboratories:type:" + laboratoryType.toUpperCase();
        redisTemplate.opsForValue().set(key, laboratories, 1, TimeUnit.HOURS);
    }

    @SuppressWarnings("unchecked")
    public Optional<List<Laboratory>> getCachedLaboratoriesByType(String laboratoryType) {
        String key = "laboratories:type:" + laboratoryType.toUpperCase();
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject instanceof List) {
            return Optional.of((List<Laboratory>) cachedObject);
        }
        return Optional.empty();
    }

    public void cacheLaboratoriesByLearningPathId(Long learningPathId, List<Laboratory> laboratories) {
        String key = "laboratory:learningpath:" + learningPathId;
        redisTemplate.opsForValue().set(key, laboratories, 1, TimeUnit.HOURS);
    }

    @SuppressWarnings("unchecked")
    public Optional<List<Laboratory>> getCachedLaboratoriesByLearningPathId(Long learningPathId) {
        String key = "laboratories:learningpath:" + learningPathId;
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject instanceof List) {
            return Optional.of((List<Laboratory>) cachedObject);
        }
        return Optional.empty();
    }

    public void invalidateCacheForLaboratory(Long laboratoryId) {
        String key = "laboratory:" + laboratoryId;
        redisTemplate.delete(key);
    }

    public void invalidateCacheForLaboratoriesByType(String laboratoryType) {
        String key = "laboratories:type:" + laboratoryType.toUpperCase();
        redisTemplate.delete(key);
    }

    public void invalidateCacheForLaboratoriesByLearningPathId(Long learningPathId) {
        String key = "laboratories:learningpath:" + learningPathId;
        redisTemplate.delete(key);
    }

    public void invalidateLaboratoriesByType(String laboratoryType) {
        String key = "laboratories:type:" + laboratoryType.toUpperCase();
        redisTemplate.delete(key);
    }

    public void invalidateAllLaboratoriesCache() {
        Set<String> keys = redisTemplate.keys("laboratories:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }

        Set<String> individualKeys = redisTemplate.keys("laboratory:*");
        if (individualKeys != null && !individualKeys.isEmpty()) {
            redisTemplate.delete(individualKeys);
        }
    }
}
