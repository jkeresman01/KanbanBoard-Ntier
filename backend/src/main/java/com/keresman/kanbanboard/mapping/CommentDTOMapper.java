package com.keresman.kanbanboard.mapping;

import com.keresman.kanbanboard.dto.CommentDTO;
import com.keresman.kanbanboard.model.Comment;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/** Mapper component that converts a {@link Comment} entity into a {@link CommentDTO}. */
@Component
public class CommentDTOMapper implements Function<Comment, CommentDTO> {

  /**
   * Maps a {@link Comment} entity to a {@link CommentDTO}.
   *
   * @param comment the entity to map
   * @return the mapped {@link CommentDTO}
   */
  @Override
  public CommentDTO apply(Comment comment) {
    return new CommentDTO(
        comment.getId(),
        comment.getTask().getId(),
        comment.getAuthorUserId(),
        comment.getMessage(),
        comment.getCreatedAt(),
        comment.getUpdatedAt());
  }
}
