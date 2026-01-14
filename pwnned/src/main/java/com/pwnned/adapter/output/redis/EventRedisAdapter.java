package com.pwnned.adapter.output.redis;

import com.pwnned.adapter.input.dto.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class EventRedisAdapter {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_KEY_PREFIX = "event:";
    private static final String ALL_EVENTS_KEY = "events:all";

    public void cacheEvent(EventDTO eventDTO) {
        String key = CACHE_KEY_PREFIX + eventDTO.eventId();
        redisTemplate.opsForValue().set(key, eventDTO, 1, TimeUnit.HOURS);
        invalidateAllEventsCache();
    }

    public Optional<EventDTO> getCachedEvent(Long eventId) {
        String key = CACHE_KEY_PREFIX + eventId;
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject instanceof EventDTO) {
            return Optional.of((EventDTO) cachedObject);
        }
        return Optional.empty();
    }

    public void cacheAllEvents(List<EventDTO> events) {
        redisTemplate.opsForValue().set(ALL_EVENTS_KEY, events, 30, TimeUnit.MINUTES);
    }

    public Optional<List<EventDTO>> getCachedAllEvents() {
        Object cachedObject = redisTemplate.opsForValue().get(ALL_EVENTS_KEY);
        if (cachedObject instanceof List) {
            return Optional.of((List<EventDTO>) cachedObject);
        }
        return Optional.empty();
    }

    public void invalidateEvent(Long eventId) {
        String key = CACHE_KEY_PREFIX + eventId;
        redisTemplate.delete(key);
        invalidateAllEventsCache();
    }

    public void invalidateAllEventsCache() {
        redisTemplate.delete(ALL_EVENTS_KEY);
    }
}