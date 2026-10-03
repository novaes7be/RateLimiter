package com.contatodireto.ratelimiterproject.core;

import com.contatodireto.ratelimiterproject.config.BucketConfig;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class TokenBucketServiceTest {

    // Test tokenBucketService.allow();

    @Test
    public void firstRequestNewKey() {

        TokenBucketService service = new TokenBucketService();
        BucketConfig config = new BucketConfig(1, 5.0);
        assertTrue(service.allow("testKey", config , Instant.now()));
    }

    @Test
    public void newKeyLastToken() {
        TokenBucketService service = new TokenBucketService();
        BucketConfig config = new BucketConfig(1, 5.0);
        assertTrue(service.allow("testKey", config, Instant.now()));
        assertFalse(service.allow("testKey", config, Instant.now()));
    }

    @Test
    public void differentKeysAreIndependent() {
        TokenBucketService service = new TokenBucketService();
        BucketConfig config = new BucketConfig(1, 5.0);

        assertTrue(service.allow("IP1", config, Instant.now()));
        assertFalse(service.allow("IP1", config, Instant.now())); // esgotou IP1

        assertTrue(service.allow("IP2", config, Instant.now())); // IP2 não foi afetado
    }

    @Test
    public void refillThroughService() {
        TokenBucketService service = new TokenBucketService();
        BucketConfig config = new BucketConfig(1, 5.0);
        Instant t1 = Instant.parse("2024-01-01T10:00:00Z");

        assertTrue(service.allow("testKey", config, t1)); // consome o único token
        assertFalse(service.allow("testKey", config, t1)); // ainda sem tokens, mesmo instante

        Instant t2 = t1.plusMillis(400); // passa tempo suficiente pra repor
        assertTrue(service.allow("testKey", config, t2)); // agora deveria ter repost
    }
}
