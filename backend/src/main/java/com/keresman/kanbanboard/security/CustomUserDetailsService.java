package com.keresman.kanbanboard.security;

import com.keresman.kanbanboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of {@link UserDetailsService} that loads user details from the {@link
 * UserRepository} by either username or email.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Loads a user by their username or email.
   *
   * @param usernameOrEmail the username or email identifying the user
   * @return the {@link UserDetails} of the authenticated user
   * @throws UsernameNotFoundException if the user is not found
   * @see UserDetailsService#loadUserByUsername(String)
   */
  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    return userRepository
        .findByUsername(usernameOrEmail)
        .or(() -> userRepository.findByEmail(usernameOrEmail))
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found: %s".formatted(usernameOrEmail)));
  }
}
