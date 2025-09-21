package com.keresman.kanbanboard.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Utility class for creating and validating JSON Web Tokens (JWT). */
@Component
public class JwtUtil {

  @Value("${security.jwt.secret}")
  private String base64Secret;

  @Value("${security.jwt.access-ttl-minutes:15}")
  private long accessTtlMinutes;

  /**
   * Generates a signed JWT access token with the given subject and claims.
   *
   * @param subject the token subject (typically the user ID)
   * @param claims additional claims to include in the token
   * @return a signed JWT access token
   */
  public String generateAccessToken(String subject, Map<String, Object> claims) {
    Instant now = Instant.now();
    return Jwts.builder()
        .setSubject(subject)
        .addClaims(claims)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plus(Duration.ofMinutes(accessTtlMinutes))))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
  }

  /**
   * Validates a JWT token by parsing and verifying its signature and expiration.
   *
   * @param token the JWT token to validate
   * @return {@code true} if the token is valid, {@code false} otherwise
   */
  public boolean isTokenValid(String token) {
    try {
      parseToken(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Parses a JWT token and returns its claims.
   *
   * @param token the JWT token to parse
   * @return the parsed {@link Jws} containing {@link Claims}
   * @throws JwtException if the token is invalid
   */
  public Jws<Claims> parseToken(String token) {
    return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
  }

  /**
   * Extracts the subject from a JWT token.
   *
   * @param token the JWT token
   * @return the subject (typically the user ID) contained in the token
   */
  public String getSubject(String token) {
    return parseToken(token).getBody().getSubject();
  }
}
