package com.keresman.kanbanboard.controller;

import com.keresman.kanbanboard.dto.CommentDTO;
import com.keresman.kanbanboard.payload.CommentCreateRequest;
import com.keresman.kanbanboard.payload.CommentUpdateRequest;
import com.keresman.kanbanboard.service.CommentService;
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

/** REST controller that provides endpoints for managing comments associated with tasks. */
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  /**
   * Retrieves all comments for a given task.
   *
   * @param taskId the ID of the task
   * @return a list of {@link CommentDTO} for the task
   */
  @GetMapping("/task/{taskId}")
  public ResponseEntity<List<com.keresman.kanbanboard.dto.CommentDTO>> getCommentsForTask(
      @PathVariable Long taskId) {
    return ResponseEntity.ok().body(commentService.getCommentsForTask(taskId));
  }

  /**
   * Retrieves a single comment by its ID.
   *
   * @param id the ID of the comment
   * @return the {@link CommentDTO} for the given ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
    return ResponseEntity.ok().body(commentService.getCommentById(id));
  }

  /**
   * Creates a new comment for a task.
   *
   * @param request the request payload containing comment details
   * @return the created {@link CommentDTO}
   */
  @PostMapping
  public ResponseEntity<CommentDTO> createComment(
      @Valid @RequestBody CommentCreateRequest request) {
    return ResponseEntity.ok().body(commentService.createComment(request));
  }

  /**
   * Updates an existing comment.
   *
   * @param id the ID of the comment to update
   * @param request the request payload containing updated fields
   * @return the updated {@link CommentDTO}
   */
  @PutMapping("/{id}")
  public ResponseEntity<CommentDTO> updateComment(
      @PathVariable Long id, @Valid @RequestBody CommentUpdateRequest request) {
    return ResponseEntity.ok().body(commentService.updateComment(id, request));
  }

  /**
   * Deletes a comment by its ID.
   *
   * @param id the ID of the comment to delete
   * @return a 204 No Content response if successful
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
    commentService.deleteComment(id);

    return ResponseEntity.noContent().build();
  }
}
