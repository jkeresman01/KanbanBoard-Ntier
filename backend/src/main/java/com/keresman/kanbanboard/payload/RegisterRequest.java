package com.keresman.kanbanboard.payload;

import com.keresman.kanbanboard.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for registering a new user account.
 *
 * @param username the desired username (must not be blank)
 * @param password the password for the account (must not be blank)
 * @param email the email address of the user (must be a valid email and not blank)
 * @param firstName the first name of the user (must not be blank)
 * @param lastName the last name of the user (must not be blank)
 * @param gender the {@link Gender} of the user
 */
public record RegisterRequest(
    @NotBlank String username,
    @NotBlank String password,
    @NotBlank @Email String email,
    @NotBlank String firstName,
    @NotBlank String lastName,
    Gender gender) {}
