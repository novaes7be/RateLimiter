package com.contatodireto.ratelimiterproject.core;

import com.contatodireto.ratelimiterproject.config.BucketConfig;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenBucketService {
    Map<String, TokenBucket> buckets = new HashMap<>();


    public boolean allow(@NonNull String key, @NonNull BucketConfig config, @NonNull Instant now) {
        TokenBucket tokenBucket = buckets.computeIfAbsent(key, k -> new TokenBucket(config.capacity()));
        tokenBucket.refill(config.refillRate(), now);
        return tokenBucket.tryConsume();
    }
}
