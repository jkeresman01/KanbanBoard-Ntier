package com.keresman.kanbanboard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "refresh_tokens",
    indexes = {@Index(name = "ix_refresh_token_token", columnList = "token", unique = true)})
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "token", nullable = false, length = 200, unique = true)
  private String token;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, name = "exipres_at")
  private Instant expiresAt;

  @Column(nullable = false, name = "is_revoked")
  private boolean revoked = false;

  public RefreshToken(String token, User user, Instant expiresAt) {
    this.token = token;
    this.user = user;
    this.expiresAt = expiresAt;
  }
}
