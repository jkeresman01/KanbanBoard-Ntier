package com.keresman.kanbanboard.payload;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for refreshing an access token using a valid refresh token.
 *
 * @param refreshToken the refresh token string (must not be blank)
 */
public record RefreshRequest(@NotBlank String refreshToken) {}
