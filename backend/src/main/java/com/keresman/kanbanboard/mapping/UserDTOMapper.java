package com.keresman.kanbanboard.mapping;

import com.keresman.kanbanboard.dto.UserDTO;
import com.keresman.kanbanboard.model.User;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/** Mapper component that converts a {@link User} entity into a {@link UserDTO}. */
@Component
public class UserDTOMapper implements Function<User, UserDTO> {

  /**
   * Maps a {@link User} entity to a {@link UserDTO}.
   *
   * @param user the entity to map
   * @return the mapped {@link UserDTO}
   */
  @Override
  public UserDTO apply(User user) {
    return new UserDTO(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        user.getGender(),
        user.getImageId());
  }
}
