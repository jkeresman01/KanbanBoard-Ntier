package com.keresman.kanbanboard.controller;

import com.keresman.kanbanboard.model.User;
import com.keresman.kanbanboard.payload.LoginRequest;
import com.keresman.kanbanboard.payload.RefreshRequest;
import com.keresman.kanbanboard.payload.RegisterRequest;
import com.keresman.kanbanboard.payload.TokenResponse;
import com.keresman.kanbanboard.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller providing authentication and authorization endpoints for user registration,
 * login, token refresh, and logout.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  /**
   * Registers a new user account and issues access and refresh tokens.
   *
   * @param request the registration request containing user details
   * @return a response containing JWT access and refresh tokens
   */
  @PostMapping("/register")
  public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.ok().body(authService.register(request));
  }

  /**
   * Authenticates a user and issues access and refresh tokens.
   *
   * @param request the login request with username/email and password
   * @return a response containing JWT access and refresh tokens
   */
  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok().body(authService.login(request));
  }

  /**
   * Issues a new access token using a valid refresh token.
   *
   * @param request the refresh request containing the refresh token
   * @return a response containing a new JWT access token and refresh token
   */
  @PostMapping("/refresh")
  public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest request) {
    return ResponseEntity.ok().body(authService.refresh(request.refreshToken()));
  }

  /**
   * Logs out the currently authenticated user by revoking refresh tokens.
   *
   * @param authentication the current authentication containing the user principal
   * @return a 204 No Content response if logout succeeds
   */
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(Authentication authentication) {
    var user = (User) authentication.getPrincipal();
    authService.logout(user);
    return ResponseEntity.noContent().build();
  }
}
