package com.example.ratelimiterdemo.ratelimiter.store;

import com.example.ratelimiterdemo.ratelimiter.config.RateLimitConfig;
import com.example.ratelimiterdemo.ratelimiter.model.UserUtilizationStatus;

import javax.annotation.PostConstruct;

/**
 * The interface Rate limit store.
 * Implement this interface to build custom stores
 * such as a database or cache
 */
public interface RateLimitStore {

    /**
     * Implement this method to initialize the store from the configurations
     * Responsibility of invoking this method is upto to implementor
     *
     * @param rateLimitConfig the rate limit config
     */
    void initStore(RateLimitConfig rateLimitConfig);

    /**
     * Gets user utilization status.
     *
     * @param api  the api
     * @param user the user
     * @return the user utilization status
     */
    UserUtilizationStatus getUserUtilizationStatus(String api, String user);
}
