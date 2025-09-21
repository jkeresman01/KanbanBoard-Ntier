package com.keresman.kanbanboard.service.impl;

import com.keresman.kanbanboard.exception.ResourceNotFoundException;
import com.keresman.kanbanboard.model.RefreshToken;
import com.keresman.kanbanboard.model.User;
import com.keresman.kanbanboard.payload.LoginRequest;
import com.keresman.kanbanboard.payload.RegisterRequest;
import com.keresman.kanbanboard.payload.TokenResponse;
import com.keresman.kanbanboard.repository.RefreshTokenRepository;
import com.keresman.kanbanboard.repository.UserRepository;
import com.keresman.kanbanboard.security.JwtUtil;
import com.keresman.kanbanboard.service.AuthService;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link AuthService} that manages user authentication, registration, JWT token
 * issuance, refresh tokens, and logout operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  /** {@inheritDoc} */
  @Transactional
  @Override
  public TokenResponse register(RegisterRequest registerReq) {
    log.info(
        "Auth: registration attempt for username='{}', email='{}'",
        registerReq.username(),
        registerReq.email());

    if (userRepository.existsByUsername(registerReq.username())) {
      log.warn("Auth: registration failed — username already taken: '{}'", registerReq.username());
      throw new IllegalArgumentException("Username already taken");
    }

    if (userRepository.existsByEmail(registerReq.email())) {
      log.warn("Auth: registration failed — email already taken: '{}'", registerReq.email());
      throw new IllegalArgumentException("Email already taken");
    }

    var user =
        User.builder()
            .username(registerReq.username())
            .password(passwordEncoder.encode(registerReq.password()))
            .email(registerReq.email())
            .firstName(registerReq.firstName())
            .lastName(registerReq.lastName())
            .gender(registerReq.gender())
            .build();

    user = userRepository.save(user);
    log.info("Auth: user registered successfully userId={}", user.getId());

    return issueTokens(user);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public TokenResponse login(LoginRequest loginReq) {
    log.info("Auth: login attempt for principal='{}'", loginReq.usernameOrEmail());
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginReq.usernameOrEmail(), loginReq.password()));
    } catch (Exception ex) {
      log.warn(
          "Auth: login failed for principal='{}' — invalid credentials",
          loginReq.usernameOrEmail());
      throw new BadCredentialsException("Invalid credentials");
    }

    User user =
        userRepository
            .findByUsername(loginReq.usernameOrEmail())
            .or(() -> userRepository.findByEmail(loginReq.usernameOrEmail()))
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    log.info("Auth: login success userId={}", user.getId());
    return issueTokens(user);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public TokenResponse refresh(String refreshTokenStr) {
    log.info("Auth: refresh token attempt");
    RefreshToken refreshToken =
        refreshTokenRepository
            .findByToken(refreshTokenStr)
            .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

    if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(Instant.now())) {
      log.warn(
          "Auth: refresh failed — token revoked or expired (tokenId={}, userId={})",
          refreshToken.getId(),
          refreshToken.getUser().getId());
      throw new BadCredentialsException("Expired or revoked refresh token");
    }

    refreshToken.setRevoked(true);
    refreshTokenRepository.save(refreshToken);
    log.debug(
        "Auth: previous refresh token revoked (tokenId={}, userId={})",
        refreshToken.getId(),
        refreshToken.getUser().getId());

    return issueTokens(refreshToken.getUser());
  }

  /** Issues new access and refresh tokens for the given user. Never logs the raw tokens. */
  private TokenResponse issueTokens(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("username", user.getUsername());

    String accessToken = jwtUtil.generateAccessToken(String.valueOf(user.getId()), claims);

    String refresh = UUID.randomUUID().toString();
    RefreshToken refreshToken =
        new RefreshToken(refresh, user, Instant.now().plus(Duration.ofDays(14)));
    refreshTokenRepository.save(refreshToken);

    log.info("Auth: issued tokens for userId={}", user.getId());
    log.debug(
        "Auth: issued refresh token entry tokenId={} for userId={}",
        refreshToken.getId(),
        user.getId());

    return new TokenResponse(accessToken, refresh);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public void logout(User user) {
    log.info("Auth: logout for userId={}", user.getId());
    var tokens = refreshTokenRepository.findAllByUser(user);
    tokens.forEach(token -> token.setRevoked(true));
    refreshTokenRepository.saveAll(tokens);
    log.debug("Auth: revoked {} refresh tokens for userId={}", tokens.size(), user.getId());
  }
}
