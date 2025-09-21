package com.keresman.kanbanboard.service;

import com.keresman.kanbanboard.dto.TaskDTO;
import com.keresman.kanbanboard.model.Status;
import com.keresman.kanbanboard.payload.TaskCreateRequest;
import com.keresman.kanbanboard.payload.TaskUpdateRequest;
import java.util.List;

/** Service interface defining operations for managing tasks in the Kanban board. */
public interface TaskService {

  /**
   * Creates a new task associated with a specific creator.
   *
   * @param request the request payload containing task details
   * @param creatorUserId the ID of the user creating the task
   * @return the created {@link TaskDTO}
   */
  TaskDTO createTask(TaskCreateRequest request, Long creatorUserId);

  /**
   * Retrieves a task by its ID.
   *
   * @param id the ID of the task
   * @return the {@link TaskDTO} for the given ID
   */
  TaskDTO getTask(Long id);

  /**
   * Retrieves all tasks in the system.
   *
   * @return a list of {@link TaskDTO} objects
   */
  List<TaskDTO> getAllTasks();

  /**
   * Retrieves all tasks filtered by a given status.
   *
   * @param status the status to filter tasks by
   * @return a list of {@link TaskDTO} objects with the given status
   */
  List<TaskDTO> getTasksByStatus(Status status);

  /**
   * Updates an existing task with new details.
   *
   * @param id the ID of the task to update
   * @param request the request payload containing updated task details
   * @return the updated {@link TaskDTO}
   */
  TaskDTO updateTask(Long id, TaskUpdateRequest request);

  /**
   * Deletes a task by its ID.
   *
   * @param id the ID of the task to delete
   */
  void deleteTask(Long id);
}
