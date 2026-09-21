package com.contatodireto.ratelimiterproject;

import org.springframework.boot.SpringApplication;

public class TestRateLimiterProjectApplication {

    public static void main(String[] args) {
        SpringApplication.from(RateLimiterProjectApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
