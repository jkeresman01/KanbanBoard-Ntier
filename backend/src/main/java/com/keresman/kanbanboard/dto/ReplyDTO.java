package com.keresman.kanbanboard.dto;

import java.time.Instant;

/**
 * Data Transfer Object representing a reply to a comment.
 *
 * @param id the unique identifier of the reply
 * @param commentId the ID of the comment this reply is associated with
 * @param authorUserId the ID of the user who authored the reply
 * @param message the textual content of the reply
 * @param createdAt the timestamp when the reply was created
 * @param updatedAt the timestamp when the reply was last updated
 */
public record ReplyDTO(
    Long id,
    Long commentId,
    Long authorUserId,
    String message,
    Instant createdAt,
    Instant updatedAt) {}
