package com.pwnned.adapter.output.redis;

import com.pwnned.domain.model.LearningPath;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LearningPathRedisAdapter {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheLearningPath(LearningPath learningPath) {
        String key = "learningpath:" + learningPath.getLearningPathId();
        redisTemplate.opsForValue().set(key, learningPath, 1, TimeUnit.HOURS);
    }

    public Optional<LearningPath> getCachedLearningPath(UUID learningPathId) {
        String key = "learningpath:" + learningPathId;
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject instanceof LearningPath) {
            return Optional.of((LearningPath) cachedObject);
        }
        return Optional.empty();
    }

    public void cacheLearningPathByDifficulty(String difficulty, List<LearningPath> learningPaths) {
        String key = "learningpaths:difficulty:" + difficulty.toUpperCase();
        redisTemplate.opsForValue().set(key, learningPaths, 1, TimeUnit.HOURS);
    }

    @SuppressWarnings("unchecked")
    public Optional<List<LearningPath>> getCachedLearningPathsByDifficulty(String difficulty) {
        String key = "learningpaths:difficulty:" + difficulty.toUpperCase();
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject instanceof List) {
            return Optional.of((List<LearningPath>) cachedObject);
        }
        return Optional.empty();
    }

    public void invalidateCacheForLearningPath(UUID learningPathId) {
        String key = "learningpath:" + learningPathId;
        redisTemplate.delete(key);
    }

    public void invalidateCacheForLearningPathsByDifficulty(String difficulty) {
        String key = "learningpaths:difficulty:" + difficulty.toUpperCase();
        redisTemplate.delete(key);
    }

    public void invalidateAllLearningPathsCache() {
        Set<String> difficultyKeys = redisTemplate.keys("learningpaths:difficulty:*");
        if (difficultyKeys != null && !difficultyKeys.isEmpty()) {
            redisTemplate.delete(difficultyKeys);
        }

        Set<String> pathKeys = redisTemplate.keys("learningpath:*");
        if (pathKeys != null && !pathKeys.isEmpty()) {
            redisTemplate.delete(pathKeys);
        }
    }
}
