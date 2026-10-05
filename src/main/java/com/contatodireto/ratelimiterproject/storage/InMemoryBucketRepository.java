package com.contatodireto.ratelimiterproject.storage;

import com.contatodireto.ratelimiterproject.core.TokenBucket;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryBucketRepository implements BucketRepository{

    private final Map<String, TokenBucket> map = new HashMap<>();


    @Override
    public Optional<TokenBucket> findByKey(String key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public void save(String key, TokenBucket bucket) {
        map.put(key, bucket);
    }
}
