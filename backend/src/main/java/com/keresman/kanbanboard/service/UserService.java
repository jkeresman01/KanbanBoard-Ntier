package com.keresman.kanbanboard.service;

import com.keresman.kanbanboard.dto.UserDTO;
import com.keresman.kanbanboard.payload.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface defining operations for managing users, including profile information and
 * profile images.
 */
public interface UserService {

  /**
   * Retrieves all users in a paginated format.
   *
   * @param pageable pagination information
   * @return a {@link Page} of {@link UserDTO} objects
   */
  Page<UserDTO> getAllUsers(Pageable pageable);

  /**
   * Retrieves a user by their unique ID.
   *
   * @param userId the ID of the user
   * @return the {@link UserDTO} representing the user
   */
  UserDTO getUserById(Long userId);

  /**
   * Updates an existing user with new details.
   *
   * @param userId the ID of the user to update
   * @param request the request payload containing updated user information
   * @return the updated {@link UserDTO}
   */
  UserDTO updateUser(Long userId, UserUpdateRequest request);

  /**
   * Uploads a profile image for the specified user.
   *
   * @param userId the ID of the user
   * @param file the profile image file to upload
   */
  void uploadProfileImage(Long userId, MultipartFile file);

  /**
   * Retrieves the profile image for the specified user.
   *
   * @param userId the ID of the user
   * @return the profile image as a byte array
   */
  byte[] getProfileImage(Long userId);

  /**
   * Deletes a user by their ID.
   *
   * @param id the ID of the user to delete
   */
  void deleteUser(Long id);
}
