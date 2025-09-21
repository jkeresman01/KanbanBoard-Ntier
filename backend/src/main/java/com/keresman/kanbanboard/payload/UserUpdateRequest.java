package com.keresman.kanbanboard.payload;

import com.keresman.kanbanboard.model.Gender;

/**
 * Request payload for updating an existing user.
 *
 * @param email the updated email address of the user (optional)
 * @param firstName the updated first name of the user (optional)
 * @param lastName the updated last name of the user (optional)
 * @param gender the updated {@link Gender} of the user (optional)
 * @param imageId the identifier of the updated profile image (optional)
 */
public record UserUpdateRequest(
    String email, String firstName, String lastName, Gender gender, String imageId) {}
