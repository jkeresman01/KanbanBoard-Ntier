package com.keresman.kanbanboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource cannot be found.
 *
 * <p>Mapped to {@link HttpStatus#NOT_FOUND} (404).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

  /**
   * Creates a new {@code ResourceNotFoundException} with the given message.
   *
   * @param message a descriptive error message
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
