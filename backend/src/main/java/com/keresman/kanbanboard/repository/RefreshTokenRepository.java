package com.keresman.kanbanboard.repository;

import com.keresman.kanbanboard.model.RefreshToken;
import com.keresman.kanbanboard.model.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** Repository interface for managing {@link RefreshToken} entities. */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  /**
   * Finds a refresh token by its token string.
   *
   * @param token the token string
   * @return an {@link Optional} containing the matching {@link RefreshToken}, if found
   */
  Optional<RefreshToken> findByToken(String token);

  /**
   * Finds all refresh tokens belonging to the given user.
   *
   * @param user the user whose refresh tokens to retrieve
   * @return a list of {@link RefreshToken} entities linked to the user
   */
  List<RefreshToken> findAllByUser(User user);

  /**
   * Deletes all refresh tokens that expired before the given time.
   *
   * @param time the cutoff time; tokens expiring before this are removed
   * @return the number of deleted tokens
   */
  @Modifying
  @Transactional
  int deleteByExpiresAtBefore(Instant time);

  /**
   * Counts the number of refresh tokens that are already expired.
   *
   * @param time the reference time; tokens expiring before this are considered expired
   * @return the count of expired tokens
   */
  long countByExpiresAtBefore(Instant time);

  /**
   * Counts the number of refresh tokens that have been revoked.
   *
   * @return the count of revoked tokens
   */
  long countByRevokedTrue();

  /**
   * Counts the number of active refresh tokens. Active tokens are not revoked and not expired as of
   * the given time.
   *
   * @param time the reference time to check expiration
   * @return the count of active (usable) tokens
   */
  long countByRevokedFalseAndExpiresAtAfter(Instant time);
}
