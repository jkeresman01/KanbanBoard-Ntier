package com.keresman.kanbanboard.repository;

import com.keresman.kanbanboard.model.Comment;
import com.keresman.kanbanboard.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for managing {@link Comment} entities. */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  /**
   * Finds all comments associated with the given task.
   *
   * @param task the task to search comments for
   * @return a list of {@link Comment} entities linked to the task
   */
  List<Comment> findAllByTask(Task task);
}
