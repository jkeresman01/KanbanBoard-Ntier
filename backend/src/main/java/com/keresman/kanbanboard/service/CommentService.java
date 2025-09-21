package com.keresman.kanbanboard.service;

import com.keresman.kanbanboard.dto.CommentDTO;
import com.keresman.kanbanboard.payload.CommentCreateRequest;
import com.keresman.kanbanboard.payload.CommentUpdateRequest;
import java.util.List;

/** Service interface defining operations for managing comments on tasks. */
public interface CommentService {

  /**
   * Retrieves all comments for a given task.
   *
   * @param taskId the ID of the task
   * @return a list of {@link CommentDTO} objects belonging to the task
   */
  List<CommentDTO> getCommentsForTask(Long taskId);

  /**
   * Retrieves a specific comment by its ID.
   *
   * @param id the ID of the comment
   * @return the {@link CommentDTO} for the given ID
   */
  CommentDTO getCommentById(Long id);

  /**
   * Creates a new comment for a task.
   *
   * @param commentCreateReq the request payload containing comment details
   * @return the created {@link CommentDTO}
   */
  CommentDTO createComment(CommentCreateRequest commentCreateReq);

  /**
   * Updates an existing comment.
   *
   * @param id the ID of the comment to update
   * @param commentUpdateReq the request payload containing updated details
   * @return the updated {@link CommentDTO}
   */
  CommentDTO updateComment(Long id, CommentUpdateRequest commentUpdateReq);

  /**
   * Deletes a comment by its ID.
   *
   * @param id the ID of the comment to delete
   */
  void deleteComment(Long id);
}
