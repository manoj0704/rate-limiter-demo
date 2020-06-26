package com.example.ratelimiterdemo.ratelimiter.service;

import com.example.ratelimiterdemo.ratelimiter.store.RateLimitStore;
import com.example.ratelimiterdemo.ratelimiter.model.UserUtilizationStatus;
import org.springframework.stereotype.Component;

@Component
public class RateLimitService {

    private final RateLimitStore rateLimitStore;

    public RateLimitService(RateLimitStore rateLimitStore) {
        this.rateLimitStore = rateLimitStore;
    }

    public boolean isCallAllowed(String api, String user) {
        UserUtilizationStatus userUtilizationStatus = rateLimitStore.getUserUtilizationStatus(api, user);
        userUtilizationStatus.removeOldest();
        return userUtilizationStatus.checkRate();
    }
}
