package com.contatodireto.ratelimiterproject.core;

import com.contatodireto.ratelimiterproject.config.BucketConfig;
import com.contatodireto.ratelimiterproject.storage.BucketRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class TokenBucketService {

    BucketRepository bucketRepository;


    public boolean allow(@NonNull String key, @NonNull BucketConfig config, @NonNull Instant now) {
        TokenBucket tokenBucket = bucketRepository.findByKey(key)
                .orElseGet(() -> new TokenBucket(config.capacity()));
        tokenBucket.refill(config.refillRate(), now);
        boolean tryConsume = tokenBucket.tryConsume();
        bucketRepository.save(key, tokenBucket);
        return tryConsume;
    }
}
