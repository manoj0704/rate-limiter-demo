package com.example.ratelimiterdemo.ratelimiter.aspect;

import com.example.ratelimiterdemo.ratelimiter.EnableRateLimit;
import com.example.ratelimiterdemo.ratelimiter.exception.RateExceededException;
import com.example.ratelimiterdemo.ratelimiter.parser.RequestParser;
import com.example.ratelimiterdemo.ratelimiter.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * The type Rate limit aspect.
 */
@Aspect
@Component
@Slf4j
public class RateLimitAspect {

    private final RateLimitService rateLimitService;

    private final RequestParser requestParser;

    public RateLimitAspect(RateLimitService rateLimitService, RequestParser requestParser) {
        this.rateLimitService = rateLimitService;
        this.requestParser = requestParser;
    }


    /**
     * Aspect which gets activated when an API invoked with {@link EnableRateLimit} annotation
     * is invoked. After fetching the userId from the request attributes, the rate is checked.
     * If the rate has exceeded then a {@link RateExceededException} is thrown, or else the
     * execution continues normally
     */
    @Around("@annotation(enableRateLimit)")
    public Object handleRate(ProceedingJoinPoint joinPoint, EnableRateLimit enableRateLimit) throws Throwable {
        String user = requestParser.getUserId();
        log.debug("user={} made a call to api={}", user, enableRateLimit.api());
        if (rateLimitService.isCallAllowed(enableRateLimit.api(), user)) {
            log.debug("user={} allowed to call api={}", user, enableRateLimit.api());
            return joinPoint.proceed();
        } else {
            log.debug("user={} blocked to call api={}", user, enableRateLimit.api());
            throw new RateExceededException();
        }
    }
}
