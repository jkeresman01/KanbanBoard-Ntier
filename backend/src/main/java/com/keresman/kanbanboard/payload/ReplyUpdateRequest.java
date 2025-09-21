package com.keresman.kanbanboard.payload;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for updating an existing reply.
 *
 * @param message the updated content of the reply (must not be blank)
 */
public record ReplyUpdateRequest(@NotBlank String message) {}
