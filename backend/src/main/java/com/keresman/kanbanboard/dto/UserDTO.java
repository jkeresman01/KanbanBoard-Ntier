package com.keresman.kanbanboard.dto;

import com.keresman.kanbanboard.model.Gender;

/**
 * Data Transfer Object representing a user in the system.
 *
 * @param id the unique identifier of the user
 * @param firstName the first name of the user
 * @param lastName the last name of the user
 * @param username the username used for authentication
 * @param email the email address of the user
 * @param gender the {@link Gender} of the user
 * @param imageId the identifier of the user's profile image stored in S3
 */
public record UserDTO(
    Long id,
    String firstName,
    String lastName,
    String username,
    String email,
    Gender gender,
    String imageId) {}
