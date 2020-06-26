package com.example.ratelimiterdemo.ratelimiter.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@ResponseStatus(code = TOO_MANY_REQUESTS, reason = "Too many requests")
public class RateExceededException extends RuntimeException {
}
