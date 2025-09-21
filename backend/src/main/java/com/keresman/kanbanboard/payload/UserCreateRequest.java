package com.keresman.kanbanboard.payload;

/**
 * Request payload for creating a new user.
 *
 * @param username the desired username for the account
 * @param password the password for the account
 * @param email the email address of the user
 * @param firstName the first name of the user
 * @param lastName the last name of the user
 */
public record UserCreateRequest(
    String username, String password, String email, String firstName, String lastName) {}
