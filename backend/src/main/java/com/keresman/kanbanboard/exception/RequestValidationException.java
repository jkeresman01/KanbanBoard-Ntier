package com.keresman.kanbanboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a request fails validation checks.
 *
 * <p>Mapped to {@link HttpStatus#BAD_REQUEST} (400).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestValidationException extends RuntimeException {

  /**
   * Creates a new {@code RequestValidationException} with the given message.
   *
   * @param message a descriptive error message
   */
  public RequestValidationException(String message) {
    super(message);
  }
}
