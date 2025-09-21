package com.keresman.kanbanboard.payload;

import com.keresman.kanbanboard.model.Label;
import com.keresman.kanbanboard.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

/**
 * Request payload for updating an existing task in the Kanban board.
 *
 * @param title the updated title of the task (must not be blank)
 * @param description the updated description of the task (optional)
 * @param status the updated {@link Status} of the task (must not be null)
 * @param labels the updated set of {@link Label} tags assigned to the task
 * @param assigneeUserId the ID of the user assigned to the task (optional)
 * @param dueAt the updated due date/time of the task (optional)
 * @param position the updated position/order of the task within the board (optional)
 */
public record TaskUpdateRequest(
    @NotBlank(message = "Title is required") String title,
    String description,
    @NotNull(message = "Status is required") Status status,
    Set<Label> labels,
    Long assigneeUserId,
    Instant dueAt,
    Double position) {}
