package com.keresman.kanbanboard.exception.functional;

/**
 * Functional interface representing a supplier of results that may throw a checked exception.
 *
 * @param <T> the type of result supplied
 * @param <E> the type of exception that may be thrown
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> {

  /**
   * Gets a result.
   *
   * @return the result
   * @throws E if an error occurs while supplying the result
   */
  T get() throws E;
}
