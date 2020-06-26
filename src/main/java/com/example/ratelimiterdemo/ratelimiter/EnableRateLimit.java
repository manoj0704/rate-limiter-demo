package com.example.ratelimiterdemo.ratelimiter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Use this annotation to enable rate limiting
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface EnableRateLimit {

    /**
     * Unique identifier to identify an API
     * Must also match the API key provided in the configurations
     *
     * @return the string
     */
    String api();
}
