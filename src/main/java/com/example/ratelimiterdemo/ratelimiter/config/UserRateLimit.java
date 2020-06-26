package com.example.ratelimiterdemo.ratelimiter.config;

import lombok.*;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "user")
@NoArgsConstructor
public class UserRateLimit {

    private String user;

    private Integer maxCallsAllowed;

    private TimeUnit timeUnit;

}
