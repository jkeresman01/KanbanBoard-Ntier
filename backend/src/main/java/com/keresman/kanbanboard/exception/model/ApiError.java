package com.keresman.kanbanboard.exception.model;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response returned by the API.
 *
 * @param path the request path where the error occurred
 * @param message a descriptive error message
 * @param statusCode the HTTP status code associated with the error
 * @param localDateTime the timestamp when the error occurred
 */
public record ApiError(
    String path, String message, Integer statusCode, LocalDateTime localDateTime) {}
