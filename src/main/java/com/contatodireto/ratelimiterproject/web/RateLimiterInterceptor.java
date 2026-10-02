package com.contatodireto.ratelimiterproject.web;

import com.contatodireto.ratelimiterproject.config.BucketConfig;
import com.contatodireto.ratelimiterproject.core.TokenBucketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final TokenBucketService tokenBucketService;
    private final Map<String, BucketConfig> bucketConfigMap = new HashMap<>();

    public RateLimiterInterceptor(TokenBucketService tokenBucketService){
       this.tokenBucketService = tokenBucketService;
       bucketConfigMap.put("/api/hello", new BucketConfig(5, 1.0));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {

        String ip = request.getRemoteAddr();
        String route = request.getRequestURI();
        String key = ip + ":" + route;
        BucketConfig config = bucketConfigMap.getOrDefault(route, new BucketConfig(10, 1.0));
        if (tokenBucketService.allow(key, config, Instant.now())) return true;
        response.setStatus(429);
        response.setHeader("Retry-After", "1");
        return false;
    }
}
