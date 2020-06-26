package com.example.ratelimiterdemo.ratelimiter.store;

import com.example.ratelimiterdemo.ratelimiter.config.RateLimitConfig;
import com.example.ratelimiterdemo.ratelimiter.config.UserRateLimit;
import com.example.ratelimiterdemo.ratelimiter.model.UserUtilizationStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Map.Entry;

@Component
public class InMemoryRateLimitStore implements RateLimitStore {

    private Map<String, Map<String, UserUtilizationStatus>> rateLimitMap;

    public InMemoryRateLimitStore(RateLimitConfig rateLimitConfig) {
        this.initStore(rateLimitConfig);
    }

    @Override
    public void initStore(RateLimitConfig rateLimitConfig) {
        rateLimitMap = new HashMap<>();

        for (Entry<String, Set<UserRateLimit>> entry : rateLimitConfig.getRateLimitMap().entrySet()) {
            String api = entry.getKey();
            Set<UserRateLimit> userRateLimits = entry.getValue();

            Map<String, UserUtilizationStatus> userUtilizationStatusMap = new HashMap<>();
            for (UserRateLimit userRateLimit : userRateLimits) {
                String user = userRateLimit.getUser();
                UserUtilizationStatus userUtilizationStatus = new UserUtilizationStatus(
                        userRateLimit.getMaxCallsAllowed(), userRateLimit.getTimeUnit());
                userUtilizationStatusMap.put(user, userUtilizationStatus);
            }

            //Handle Default Rate
            UserUtilizationStatus defaultUserUtilizationStatus = new UserUtilizationStatus(
                    rateLimitConfig.getDefaultMaxCallsAllowed(), rateLimitConfig.getDefaultTimeUnit());
            userUtilizationStatusMap.put(null, defaultUserUtilizationStatus);
            rateLimitMap.put(api, userUtilizationStatusMap);
        }
    }

    @Override
    public UserUtilizationStatus getUserUtilizationStatus(String api, String user) {
        Map<String, UserUtilizationStatus> apiUtilization = rateLimitMap.get(api);
        UserUtilizationStatus userUtilizationStatus = apiUtilization.get(user);

        //Handling defaults
        if (userUtilizationStatus == null) {
            userUtilizationStatus = apiUtilization.get(null);
        }
        return userUtilizationStatus;
    }
}
