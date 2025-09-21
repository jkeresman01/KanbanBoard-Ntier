package com.keresman.kanbanboard.service;

import com.keresman.kanbanboard.dto.ReplyDTO;
import com.keresman.kanbanboard.payload.ReplyCreateRequest;
import com.keresman.kanbanboard.payload.ReplyUpdateRequest;
import java.util.List;

/** Service interface defining operations for managing replies to comments. */
public interface ReplyService {

  /**
   * Retrieves all replies for a given comment.
   *
   * @param commentId the ID of the comment
   * @return a list of {@link ReplyDTO} objects belonging to the comment
   */
  List<ReplyDTO> getRepliesForComment(Long commentId);

  /**
   * Retrieves a specific reply by its ID.
   *
   * @param id the ID of the reply
   * @return the {@link ReplyDTO} for the given ID
   */
  ReplyDTO getReplyById(Long id);

  /**
   * Creates a new reply for a comment.
   *
   * @param request the request payload containing reply details
   * @return the created {@link ReplyDTO}
   */
  ReplyDTO createReply(ReplyCreateRequest request);

  /**
   * Updates an existing reply.
   *
   * @param id the ID of the reply to update
   * @param request the request payload containing updated details
   * @return the updated {@link ReplyDTO}
   */
  ReplyDTO updateReply(Long id, ReplyUpdateRequest request);

  /**
   * Deletes a reply by its ID.
   *
   * @param id the ID of the reply to delete
   */
  void deleteReply(Long id);
}
