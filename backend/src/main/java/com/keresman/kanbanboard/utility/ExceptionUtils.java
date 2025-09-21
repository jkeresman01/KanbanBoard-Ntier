package com.keresman.kanbanboard.utility;

import com.keresman.kanbanboard.exception.functional.ThrowingExceptionTask;
import com.keresman.kanbanboard.exception.functional.ThrowingSupplier;

public final class ExceptionUtils {

  private ExceptionUtils() {
    // Suppress default constructor, ensuring non-instantiability
  }

  /**
   * Executes a task that may throw a checked exception, wrapping any exception in a {@link
   * RuntimeException}.
   *
   * @param task the task to execute
   * @param message the error message to use in the RuntimeException
   * @param <E> the type of checked exception that may be thrown
   * @throws RuntimeException if the task throws any exception
   */
  public static <E extends Exception> void executeUnchecked(
      ThrowingExceptionTask<E> task, String message) {
    try {
      task.execute();
    } catch (Exception e) {
      throw new RuntimeException(message, e);
    }
  }

  /**
   * Executes a supplier that returns a value and may throw a checked exception, wrapping any
   * exception in a {@link RuntimeException}.
   *
   * @param supplier the supplier to execute
   * @param message the error message to use in the RuntimeException
   * @param <T> the type of the return value
   * @param <E> the type of checked exception that may be thrown
   * @return the value produced by the supplier
   * @throws RuntimeException if the supplier throws any exception
   */
  public static <T, E extends Exception> T callUnchecked(
      ThrowingSupplier<T, E> supplier, String message) {
    try {
      return supplier.get();
    } catch (Exception e) {
      throw new RuntimeException(message, e);
    }
  }
}
