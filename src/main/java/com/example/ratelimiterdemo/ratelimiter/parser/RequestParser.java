package com.example.ratelimiterdemo.ratelimiter.parser;


/**
 * The interface Request parser.
 * Implement this interface to build custom request parsers
 * to fetch the user information
 */
public interface RequestParser {
    /**
     * Gets user id.
     *
     * @return the user id if found or else null
     */
    String getUserId();
}
