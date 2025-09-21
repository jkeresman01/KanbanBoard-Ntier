package com.keresman.kanbanboard.payload;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for authenticating a user.
 *
 * @param usernameOrEmail the username or email used for login (must not be blank)
 * @param password the user's password (must not be blank)
 */
public record LoginRequest(@NotBlank String usernameOrEmail, @NotBlank String password) {}
