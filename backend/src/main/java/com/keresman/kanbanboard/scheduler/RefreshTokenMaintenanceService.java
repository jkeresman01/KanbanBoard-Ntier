package com.keresman.kanbanboard.scheduler;

/**
 * Periodic maintenance for refresh tokens:
 *
 * <ul>
 *   <li>Purge expired tokens on a schedule
 *   <li>Log token statistics on a schedule
 * </ul>
 */
public interface RefreshTokenMaintenanceService {

  /** Purges expired refresh tokens based on the configured schedule. */
  void purgeExpiredTokens();

  /** Logs daily token statistics (total, active, expired, revoked). */
  void logDailyTokenStats();
}
