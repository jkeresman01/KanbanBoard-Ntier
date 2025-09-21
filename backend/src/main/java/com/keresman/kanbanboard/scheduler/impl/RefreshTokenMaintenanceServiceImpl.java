package com.keresman.kanbanboard.scheduler.impl;

import com.keresman.kanbanboard.repository.RefreshTokenRepository;
import com.keresman.kanbanboard.scheduler.RefreshTokenMaintenanceService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link RefreshTokenMaintenanceService}. Periodic maintenance for refresh
 * tokens: - purge expired tokens - log stats daily
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenMaintenanceServiceImpl implements RefreshTokenMaintenanceService {

  private final RefreshTokenRepository refreshTokenRepository;

  /**
   * {@InheritDoc}
   *
   * @see RefreshTokenMaintenanceService#purgeExpiredTokens()
   */
  @Override
  @Scheduled(cron = "${scheduling.tokens.purge.cron}", zone = "${scheduling.zone}")
  @Transactional
  public void purgeExpiredTokens() {
    Instant now = Instant.now();
    int deleted = refreshTokenRepository.deleteByExpiresAtBefore(now);
    log.info("TokenMaintenance: purged {} expired refresh tokens at {}", deleted, now);
  }

  /**
   * {@InheritDoc}
   *
   * @see RefreshTokenMaintenanceService#logDailyTokenStats()
   */
  @Override
  @Scheduled(cron = "${scheduling.tokens.stats.cron}", zone = "${scheduling.zone}")
  public void logDailyTokenStats() {
    Instant now = Instant.now();

    long total = refreshTokenRepository.count();
    long expired = refreshTokenRepository.countByExpiresAtBefore(now);
    long revoked = refreshTokenRepository.countByRevokedTrue();
    long active = refreshTokenRepository.countByRevokedFalseAndExpiresAtAfter(now);

    log.info(
        "TokenMaintenance: daily stats — total={}, active={}, expired={}, revoked={}",
        total,
        active,
        expired,
        revoked);
  }
}
