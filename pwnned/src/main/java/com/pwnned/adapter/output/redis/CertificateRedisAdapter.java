package com.pwnned.adapter.output.redis;

import com.pwnned.domain.model.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CertificateRedisAdapter {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheCertificateBySerialNumber(String serialNumber, Certificate certificates) {
        String key = "certificate:serialnumber:" + serialNumber;
        redisTemplate.opsForValue().set(key, certificates, 1, TimeUnit.HOURS);
    }

    public  Optional<Certificate> getCertificateBySerialNumber(String serialNumber) {
        String key = "certificate:serialnumber:" + serialNumber;
        Object cachedObject = redisTemplate.opsForValue().get(key);

        if (cachedObject instanceof Certificate) {
            return Optional.of((Certificate) cachedObject);
        }
        return Optional.empty();
    }

    public void invalidateCacheForCertificateBySerialNumber(String serialNumber) {
        String key = "certificate:serialnumber:" + serialNumber;
        redisTemplate.delete(key);
    }

    public void deleteAllCachedCertificates() {
        Set<String> keys = redisTemplate.keys("certificate:*");
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
