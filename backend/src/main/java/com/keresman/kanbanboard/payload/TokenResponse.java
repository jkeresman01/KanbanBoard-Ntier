package com.keresman.kanbanboard.payload;

/**
 * Response payload containing authentication tokens issued by the system.
 *
 * @param accessToken the JWT access token used for authorizing API requests
 * @param refreshToken the JWT refresh token used to obtain new access tokens
 */
public record TokenResponse(String accessToken, String refreshToken) {}
