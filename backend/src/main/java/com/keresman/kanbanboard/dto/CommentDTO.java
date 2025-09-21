package com.keresman.kanbanboard.dto;

import java.time.Instant;

/**
 * Data Transfer Object representing a comment on a task.
 *
 * @param id the unique identifier of the comment
 * @param taskId the ID of the task this comment belongs to
 * @param authorUserId the ID of the user who authored the comment
 * @param message the textual content of the comment
 * @param createdAt the timestamp when the comment was created
 * @param updatedAt the timestamp when the comment was last updated
 */
public record CommentDTO(
    Long id,
    Long taskId,
    Long authorUserId,
    String message,
    Instant createdAt,
    Instant updatedAt) {}
