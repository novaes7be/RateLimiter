package com.contatodireto.ratelimiterproject.storage;

import com.contatodireto.ratelimiterproject.core.TokenBucket;
import java.util.Optional;

public interface BucketRepository {

    Optional<TokenBucket> findByKey(String key);

    void save(String key, TokenBucket bucket);
}