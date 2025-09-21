package com.keresman.kanbanboard.repository;

import com.keresman.kanbanboard.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for managing {@link User} entities. */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Finds a user by their username.
   *
   * @param username the username to search for
   * @return an {@link Optional} containing the {@link User}, if found
   */
  Optional<User> findByUsername(String username);

  /**
   * Finds a user by their email address.
   *
   * @param email the email to search for
   * @return an {@link Optional} containing the {@link User}, if found
   */
  Optional<User> findByEmail(String email);

  /**
   * Checks if a user with the given username exists.
   *
   * @param username the username to check
   * @return {@code true} if a user exists with the given username, otherwise {@code false}
   */
  boolean existsByUsername(String username);

  /**
   * Checks if a user with the given email exists.
   *
   * @param email the email to check
   * @return {@code true} if a user exists with the given email, otherwise {@code false}
   */
  boolean existsByEmail(String email);
}
