package com.keresman.kanbanboard.service.impl;

import com.keresman.kanbanboard.dto.TaskDTO;
import com.keresman.kanbanboard.exception.ResourceNotFoundException;
import com.keresman.kanbanboard.mapping.TaskDTOMapper;
import com.keresman.kanbanboard.model.Status;
import com.keresman.kanbanboard.model.Task;
import com.keresman.kanbanboard.payload.TaskCreateRequest;
import com.keresman.kanbanboard.payload.TaskUpdateRequest;
import com.keresman.kanbanboard.repository.TaskRepository;
import com.keresman.kanbanboard.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link TaskService} that manages CRUD operations for tasks in the Kanban board.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final TaskDTOMapper taskDTOMapper;

  /** {@inheritDoc} */
  @Transactional
  @Override
  public TaskDTO createTask(TaskCreateRequest request, Long creatorUserId) {
    log.info(
        "Creating new task with title='{}' by creatorUserId={}", request.title(), creatorUserId);

    Task task =
        Task.builder()
            .title(request.title())
            .description(request.description())
            .status(request.status())
            .labels(request.labels())
            .creatorUserId(creatorUserId)
            .assigneeUserId(request.assigneeUserId())
            .dueAt(request.dueAt())
            .position(request.position())
            .build();

    Task saved = taskRepository.save(task);
    log.info("Created task id={} with title='{}'", saved.getId(), saved.getTitle());

    return taskDTOMapper.apply(saved);
  }

  /** {@inheritDoc} */
  @Override
  public TaskDTO getTask(Long id) {
    log.info("Fetching task by id={}", id);
    return taskRepository
        .findById(id)
        .map(taskDTOMapper)
        .orElseThrow(
            () -> new ResourceNotFoundException("Task not found with id [%s]".formatted(id)));
  }

  /** {@inheritDoc} */
  @Override
  public List<TaskDTO> getAllTasks() {
    log.info("Fetching all tasks");
    List<TaskDTO> tasks = taskRepository.findAll().stream().map(taskDTOMapper).toList();

    log.info("Fetched {} tasks", tasks.size());
    return tasks;
  }

  /** {@inheritDoc} */
  @Override
  public List<TaskDTO> getTasksByStatus(Status status) {
    log.info("Fetching tasks with status={}", status);
    List<TaskDTO> tasks =
        taskRepository.findAllByStatus(status).stream().map(taskDTOMapper).toList();

    log.info("Fetched {} tasks with status={}", tasks.size(), status);
    return tasks;
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public TaskDTO updateTask(Long id, TaskUpdateRequest request) {
    log.info("Updating task id={}", id);

    Task task =
        taskRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Task not found with id [%s]".formatted(id)));

    task.setTitle(request.title());
    task.setDescription(request.description());
    task.setStatus(request.status());
    task.getLabels().clear();

    if (request.labels() != null) {
      task.getLabels().addAll(request.labels());
    }

    task.setAssigneeUserId(request.assigneeUserId());
    task.setDueAt(request.dueAt());
    task.setPosition(request.position());

    Task updated = taskRepository.save(task);
    log.info("Updated task id={} with new title='{}'", id, updated.getTitle());

    return taskDTOMapper.apply(updated);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public void deleteTask(Long id) {
    log.info("Deleting task id={}", id);

    if (!taskRepository.existsById(id)) {
      log.warn("Delete failed — task not found id={}", id);
      throw new ResourceNotFoundException("Task not found with id [%s]".formatted(id));
    }

    taskRepository.deleteById(id);
    log.info("Deleted task id={}", id);
  }
}
