package com.keresman.kanbanboard.payload;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for updating an existing comment.
 *
 * @param message the updated content of the comment (must not be blank)
 */
public record CommentUpdateRequest(@NotBlank String message) {}
