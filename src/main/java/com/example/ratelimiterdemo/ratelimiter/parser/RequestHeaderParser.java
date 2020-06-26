package com.example.ratelimiterdemo.ratelimiter.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Parse the headers sent as part of the request
 * to fetch the userId
 */
@Slf4j
@Component
public class RequestHeaderParser implements RequestParser {
    @Override
    public String getUserId() {
        HttpServletRequest servletRequest;
        try {
            servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            return servletRequest.getHeader("user");
        } catch (IllegalStateException e) {
            log.error("Unable to fetch RequestAttributes. Please check if the annotation " +
                    "@EnableRateLimit has been configured correctly", e);
        }
        return null;
    }
}
