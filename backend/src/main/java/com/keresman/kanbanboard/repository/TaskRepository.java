package com.keresman.kanbanboard.repository;

import com.keresman.kanbanboard.model.Status;
import com.keresman.kanbanboard.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for managing {@link Task} entities. */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  /**
   * Finds all tasks with the given status.
   *
   * @param status the {@link Status} to filter tasks by
   * @return a list of {@link Task} entities with the specified status
   */
  List<Task> findAllByStatus(Status status);
}
