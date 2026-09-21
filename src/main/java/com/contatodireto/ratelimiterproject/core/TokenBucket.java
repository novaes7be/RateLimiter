package com.contatodireto.ratelimiterproject.core;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@Getter
@Setter
public class TokenBucket {
    double currentTokens;
    double capacity;
    Instant lastRefillTimeStamp;

    public TokenBucket (double capacity){
        this.capacity = capacity;
        this.currentTokens = capacity;
        this.lastRefillTimeStamp = Instant.now();
    }

    public void refill(double refillRate, Instant now) {
        Duration duration = Duration.between(lastRefillTimeStamp, now);
        double secondsElapsed = duration.toMillis() / 1000.0;
        double tokensToAdd = secondsElapsed * refillRate;
        this.currentTokens = Math.min(currentTokens + tokensToAdd, capacity);
        this.lastRefillTimeStamp = now;
    }

    public boolean tryConsume() {
        if (currentTokens >= 1) {
            currentTokens = currentTokens - 1;
            return true;
        } else {
            return false;
        }
    }
}
