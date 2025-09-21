package com.keresman.kanbanboard.repository;

import com.keresman.kanbanboard.model.Comment;
import com.keresman.kanbanboard.model.Reply;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for managing {@link Reply} entities. */
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

  /**
   * Finds all replies associated with the given comment.
   *
   * @param comment the comment to search replies for
   * @return a list of {@link Reply} entities linked to the comment
   */
  List<Reply> findAllByComment(Comment comment);
}
