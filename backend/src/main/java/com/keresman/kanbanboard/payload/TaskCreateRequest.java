package com.keresman.kanbanboard.payload;

import com.keresman.kanbanboard.model.Label;
import com.keresman.kanbanboard.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

/**
 * Request payload for creating a new task in the Kanban board.
 *
 * @param title the title of the task (must not be blank)
 * @param description an optional description of the task
 * @param status the initial {@link Status} of the task (must not be null)
 * @param labels a set of {@link Label} tags to categorize the task
 * @param assigneeUserId the ID of the user assigned to the task (optional)
 * @param dueAt the due date/time of the task (optional)
 * @param position the position/order of the task within the board (optional)
 */
public record TaskCreateRequest(
    @NotBlank(message = "Title is required") String title,
    String description,
    @NotNull(message = "Status is required") Status status,
    Set<Label> labels,
    Long assigneeUserId,
    Instant dueAt,
    Double position) {}
