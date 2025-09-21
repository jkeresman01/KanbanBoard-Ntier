package com.keresman.kanbanboard.exception.handler;

import com.keresman.kanbanboard.exception.RequestValidationException;
import com.keresman.kanbanboard.exception.ResourceNotFoundException;
import com.keresman.kanbanboard.exception.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler that intercepts and translates exceptions into consistent {@link
 * ApiError} responses.
 */
@ControllerAdvice
public class DefaultExceptionHandler {

  /**
   * Handles cases where a requested resource is not found.
   *
   * @param ex the exception
   * @param request the HTTP request
   * @return a {@link ResponseEntity} containing an {@link ApiError} with status 404
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFoundException(
      ResourceNotFoundException ex, HttpServletRequest request) {
    return build(HttpStatus.NOT_FOUND, request, ex.getMessage());
  }

  /**
   * Handles validation errors on method arguments annotated with {@code @Valid}.
   *
   * @param ex the exception
   * @param request the HTTP request
   * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpServletRequest request) {

    return getApiErrorResponseEntity(ex, request);
  }

  /**
   * Handles binding errors when request parameters or payloads cannot be bound to objects.
   *
   * @param ex the exception
   * @param request the HTTP request
   * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400
   */
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiError> handleBindException(
      BindException ex, HttpServletRequest request) {

    return getApiErrorResponseEntity(ex, request);
  }

  private ResponseEntity<ApiError> getApiErrorResponseEntity(
      BindException ex, HttpServletRequest request) {
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                err ->
                    err.getField()
                        + ": "
                        + (err.getDefaultMessage() == null ? "invalid" : err.getDefaultMessage()))
            .collect(Collectors.joining("; "));

    if (message.isBlank()) {
      message = "Validation failed";
    }
    return build(HttpStatus.BAD_REQUEST, request, message);
  }

  /**
   * Handles validation constraint violations.
   *
   * @param ex the exception
   * @param request the HTTP request
   * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiError> handleConstraintViolation(
      ConstraintViolationException ex, HttpServletRequest request) {

    String message =
        ex.getConstraintViolations().stream()
            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
            .collect(Collectors.joining("; "));

    if (message.isBlank()) {
      message = "Constraint violation";
    }
    return build(HttpStatus.BAD_REQUEST, request, message);
  }

  /**
   * Handles custom request validation failures.
   *
   * @param ex the exception
   * @param request the HTTP request
   * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400
   */
  @ExceptionHandler(RequestValidationException.class)
  public ResponseEntity<ApiError> handleRequestValidationException(
      RequestValidationException ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now());

    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles malformed request bodies that cannot be deserialized.
   *
   * @param ex the exception
   * @param request the HTTP request
   * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleNotReadable(
      HttpMessageNotReadableException ex, HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST, request, "Malformed request body");
  }

  /**
   * Handles authentication failures due to invalid credentials.
   *
   * @param ex the exception
   * @param request the HTTP request
   * @return a {@link ResponseEntity} containing an {@link ApiError} with status 401
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiError> handleBadCredentials(
      BadCredentialsException ex, HttpServletRequest request) {
    return build(HttpStatus.UNAUTHORIZED, request, "Invalid credentials");
  }

  /**
   * Handles any unexpected exceptions not covered by more specific handlers.
   *
   * @param ex the exception
   * @param request the HTTP request
   * @return a {@link ResponseEntity} containing an {@link ApiError} with status 500
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, request, "Unexpected error occurred");
  }

  private ResponseEntity<ApiError> build(
      HttpStatus status, HttpServletRequest request, String message) {
    var apiError =
        new ApiError(request.getRequestURI(), message, status.value(), LocalDateTime.now());
    return ResponseEntity.status(status).body(apiError);
  }
}
