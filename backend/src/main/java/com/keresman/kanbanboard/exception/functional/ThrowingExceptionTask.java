package com.keresman.kanbanboard.exception.functional;

/**
 * Functional interface representing a task that can throw a checked exception.
 *
 * @param <E> the type of exception that may be thrown
 */
@FunctionalInterface
public interface ThrowingExceptionTask<E extends Exception> {

  /**
   * Executes throwing exception task
   *
   * @throws E exception
   */
  void execute() throws E;
}
