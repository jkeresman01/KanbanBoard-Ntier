package com.keresman.kanbanboard.dto;

import com.keresman.kanbanboard.model.Label;
import com.keresman.kanbanboard.model.Status;
import java.time.Instant;
import java.util.Set;

/**
 * Data Transfer Object representing a task in the Kanban board.
 *
 * @param id the unique identifier of the task
 * @param title the title of the task
 * @param description a detailed description of the task
 * @param status the current {@link Status} of the task (e.g., TODO, IN_PROGRESS, DONE)
 * @param labels a set of {@link Label} tags assigned to the task
 * @param creatorUserId the ID of the user who created the task
 * @param assigneeUserId the ID of the user assigned to the task
 * @param dueAt the due date/time of the task
 * @param position the position/order of the task within a column or board
 * @param createdAt the timestamp when the task was created
 * @param updatedAt the timestamp when the task was last updated
 */
public record TaskDTO(
    Long id,
    String title,
    String description,
    Status status,
    Set<Label> labels,
    Long creatorUserId,
    Long assigneeUserId,
    Instant dueAt,
    Double position,
    Instant createdAt,
    Instant updatedAt) {}
