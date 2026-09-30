package com.contatodireto.ratelimiterproject.web;

import com.contatodireto.ratelimiterproject.config.BucketConfig;
import com.contatodireto.ratelimiterproject.core.TokenBucket;
import com.contatodireto.ratelimiterproject.core.TokenBucketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ip = request.getRemoteAddr();
        String route = request.getRequestURI();
        String key = ip + ":" + route;



        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
