package com.keresman.kanbanboard.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new comment on a task.
 *
 * @param taskId the ID of the task the comment belongs to (must not be null)
 * @param authorUserId the ID of the user authoring the comment (must not be null)
 * @param message the content of the comment (must not be blank)
 */
public record CommentCreateRequest(
    @NotNull Long taskId, @NotNull Long authorUserId, @NotBlank String message) {}
