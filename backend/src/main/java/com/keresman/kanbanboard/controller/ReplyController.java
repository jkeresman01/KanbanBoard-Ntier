package com.keresman.kanbanboard.controller;

import com.keresman.kanbanboard.dto.ReplyDTO;
import com.keresman.kanbanboard.payload.ReplyCreateRequest;
import com.keresman.kanbanboard.payload.ReplyUpdateRequest;
import com.keresman.kanbanboard.service.ReplyService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller that provides endpoints for managing replies associated with comments. */
@RestController
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyController {

  private final ReplyService replyService;

  /**
   * Retrieves all replies for a given comment.
   *
   * @param commentId the ID of the comment
   * @return a list of {@link ReplyDTO} associated with the comment
   */
  @GetMapping("/comment/{commentId}")
  public ResponseEntity<List<ReplyDTO>> getRepliesForComment(@PathVariable Long commentId) {
    return ResponseEntity.ok().body(replyService.getRepliesForComment(commentId));
  }

  /**
   * Retrieves a single reply by its ID.
   *
   * @param id the ID of the reply
   * @return the {@link ReplyDTO} for the given reply ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<ReplyDTO> getReplyById(@PathVariable Long id) {
    return ResponseEntity.ok().body(replyService.getReplyById(id));
  }

  /**
   * Creates a new reply for a comment.
   *
   * @param request the request payload containing reply details
   * @return the created {@link ReplyDTO}
   */
  @PostMapping
  public ResponseEntity<ReplyDTO> createReply(@Valid @RequestBody ReplyCreateRequest request) {
    return ResponseEntity.ok().body(replyService.createReply(request));
  }

  /**
   * Updates an existing reply.
   *
   * @param id the ID of the reply to update
   * @param request the request payload containing updated fields
   * @return the updated {@link ReplyDTO}
   */
  @PutMapping("/{id}")
  public ResponseEntity<ReplyDTO> updateReply(
      @PathVariable Long id, @Valid @RequestBody ReplyUpdateRequest request) {
    return ResponseEntity.ok().body(replyService.updateReply(id, request));
  }

  /**
   * Deletes a reply by its ID.
   *
   * @param id the ID of the reply to delete
   * @return a 204 No Content response if deletion succeeds
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
    replyService.deleteReply(id);
    return ResponseEntity.noContent().build();
  }
}
