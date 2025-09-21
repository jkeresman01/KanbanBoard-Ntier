package com.keresman.kanbanboard.mapping;

import com.keresman.kanbanboard.dto.TaskDTO;
import com.keresman.kanbanboard.model.Task;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/** Mapper component that converts a {@link Task} entity into a {@link TaskDTO}. */
@Component
public class TaskDTOMapper implements Function<Task, TaskDTO> {

  /**
   * Maps a {@link Task} entity to a {@link TaskDTO}.
   *
   * @param task the entity to map
   * @return the mapped {@link TaskDTO}
   */
  @Override
  public TaskDTO apply(Task task) {
    return new TaskDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getStatus(),
        task.getLabels(),
        task.getCreatorUserId(),
        task.getAssigneeUserId(),
        task.getDueAt(),
        task.getPosition(),
        task.getCreatedAt(),
        task.getUpdatedAt());
  }
}
