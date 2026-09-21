package com.contatodireto.ratelimiterproject.core;

import com.contatodireto.ratelimiterproject.config.BucketConfig;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TokenBucketService {
    Map<String, TokenBucket> buckets = new HashMap<>();

    public boolean allow(String key, BucketConfig config) {
        TokenBucket tokenBucket = buckets.computeIfAbsent(key, k -> new TokenBucket(config.capacity()));
        tokenBucket.refill(config.refillRate(), Instant.now());
        return tokenBucket.tryConsume();
    }
}
