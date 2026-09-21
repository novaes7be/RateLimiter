package com.contatodireto.ratelimiterproject;

import com.contatodireto.ratelimiterproject.core.TokenBucket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

public class TokenBucketTest {

    double capacity = 10;

    @Test
    public void consumeAvailableTokens() {
        TokenBucket tokenBucket = new TokenBucket(capacity);
        assertTrue(tokenBucket.tryConsume());
        assertEquals(9, tokenBucket.getCurrentTokens());
    }

    @Test
    public void consumeWithoutTokens(){
        TokenBucket tokenBucket = new TokenBucket(capacity);
        while (tokenBucket.getCurrentTokens() > 0) {
            tokenBucket.tryConsume();
        }
        assertFalse(tokenBucket.tryConsume());
        assertEquals(0, tokenBucket.getCurrentTokens());
    }

    @Test
    public void refillDoesNotTrespassCapacity() {
        Instant t1 = Instant.parse("2024-01-01T10:00:00Z");
        Instant t2 = t1.plusMillis(2000); // 0.4s depois
        TokenBucket tokenBucket = new TokenBucket(capacity);
        tokenBucket.setCurrentTokens(8);
        tokenBucket.setLastRefillTimeStamp(t1);
        tokenBucket.refill(2.0, t2);
        assertEquals(10, tokenBucket.getCurrentTokens());
    }

    @Test
    public void refillPartiallyCorrect() {
        Instant t1 = Instant.parse("2024-01-01T10:00:00Z");
        Instant t2 = t1.plusMillis(400);

        TokenBucket tokenBucket = new TokenBucket(capacity);
        tokenBucket.setCurrentTokens(0);
        tokenBucket.setLastRefillTimeStamp(t1);
        tokenBucket.refill(5.0, t2);
        assertEquals(2, tokenBucket.getCurrentTokens());
    }
}
