package com.example.ratelimiterdemo.ratelimiter.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties(prefix = "rate.limits")
@EnableConfigurationProperties
@Slf4j
@Getter
@Setter
@ToString
public class RateLimitConfig {
    private Integer defaultMaxCallsAllowed;
    private TimeUnit defaultTimeUnit;
    private Map<String, Set<UserRateLimit>> throttleRateMap = new HashMap<>();
}
