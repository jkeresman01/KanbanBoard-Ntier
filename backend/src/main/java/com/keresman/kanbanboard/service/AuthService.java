package com.keresman.kanbanboard.service;

import com.keresman.kanbanboard.model.User;
import com.keresman.kanbanboard.payload.LoginRequest;
import com.keresman.kanbanboard.payload.RegisterRequest;
import com.keresman.kanbanboard.payload.TokenResponse;

/**
 * Service interface defining authentication operations such as user registration, login, token
 * refresh, and logout.
 */
public interface AuthService {

  /**
   * Registers a new user and issues authentication tokens.
   *
   * @param registerRequest the request payload containing user registration details
   * @return a {@link TokenResponse} containing access and refresh tokens
   */
  TokenResponse register(RegisterRequest registerRequest);

  /**
   * Authenticates a user and issues authentication tokens.
   *
   * @param loginRequest the request payload containing login credentials
   * @return a {@link TokenResponse} containing access and refresh tokens
   */
  TokenResponse login(LoginRequest loginRequest);

  /**
   * Refreshes the access token using a valid refresh token.
   *
   * @param refreshTokenStr the refresh token string
   * @return a {@link TokenResponse} containing new access and refresh tokens
   */
  TokenResponse refresh(String refreshTokenStr);

  /**
   * Logs out a user by revoking their refresh tokens.
   *
   * @param user the user to log out
   */
  void logout(User user);
}
