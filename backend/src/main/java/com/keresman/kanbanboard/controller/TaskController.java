package com.keresman.kanbanboard.controller;

import com.keresman.kanbanboard.dto.TaskDTO;
import com.keresman.kanbanboard.model.Status;
import com.keresman.kanbanboard.model.User;
import com.keresman.kanbanboard.payload.TaskCreateRequest;
import com.keresman.kanbanboard.payload.TaskUpdateRequest;
import com.keresman.kanbanboard.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** REST controller that provides endpoints for managing tasks within the Kanban board. */
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  /**
   * Creates a new task for the authenticated user.
   *
   * @param request the request payload containing task details
   * @param authentication the authentication containing the current user
   * @return the created {@link TaskDTO}
   */
  @PostMapping
  public ResponseEntity<TaskDTO> createTask(
      @Valid @RequestBody TaskCreateRequest request, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    return ResponseEntity.ok(taskService.createTask(request, user.getId()));
  }

  /**
   * Retrieves a single task by its ID.
   *
   * @param id the ID of the task
   * @return the {@link TaskDTO} for the given ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.getTask(id));
  }

  /**
   * Retrieves all tasks or filters them by status if provided.
   *
   * @param status the optional {@link Status} to filter tasks
   * @return a list of {@link TaskDTO}
   */
  @GetMapping
  public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam(required = false) Status status) {
    if (status != null) {
      return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }
    return ResponseEntity.ok(taskService.getAllTasks());
  }

  /**
   * Updates an existing task by its ID.
   *
   * @param id the ID of the task to update
   * @param request the request payload containing updated task details
   * @return the updated {@link TaskDTO}
   */
  @PutMapping("/{id}")
  public ResponseEntity<TaskDTO> updateTask(
      @PathVariable Long id, @Valid @RequestBody TaskUpdateRequest request) {
    return ResponseEntity.ok(taskService.updateTask(id, request));
  }

  /**
   * Deletes a task by its ID.
   *
   * @param id the ID of the task to delete
   * @return a 204 No Content response if deletion succeeds
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }
}
