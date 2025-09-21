package com.keresman.kanbanboard.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new reply to a comment.
 *
 * @param commentId the ID of the comment this reply belongs to (must not be null)
 * @param authorUserId the ID of the user authoring the reply (must not be null)
 * @param message the content of the reply (must not be blank)
 */
public record ReplyCreateRequest(
    @NotNull Long commentId, @NotNull Long authorUserId, @NotBlank String message) {}
