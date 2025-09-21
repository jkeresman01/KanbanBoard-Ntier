package com.keresman.kanbanboard.mapping;

import com.keresman.kanbanboard.dto.ReplyDTO;
import com.keresman.kanbanboard.model.Reply;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/** Mapper component that converts a {@link Reply} entity into a {@link ReplyDTO}. */
@Component
public class ReplyDTOMapper implements Function<Reply, ReplyDTO> {

  /**
   * Maps a {@link Reply} entity to a {@link ReplyDTO}.
   *
   * @param reply the entity to map
   * @return the mapped {@link ReplyDTO}
   */
  @Override
  public ReplyDTO apply(Reply reply) {
    return new ReplyDTO(
        reply.getId(),
        reply.getComment().getId(),
        reply.getAuthorUserId(),
        reply.getMessage(),
        reply.getCreatedAt(),
        reply.getUpdatedAt());
  }
}
