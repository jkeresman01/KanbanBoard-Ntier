package com.keresman.kanbanboard.controller;

import com.keresman.kanbanboard.dto.UserDTO;
import com.keresman.kanbanboard.payload.UserUpdateRequest;
import com.keresman.kanbanboard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** REST controller that provides endpoints for managing users and their profile images. */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * Retrieves all users with pagination support.
   *
   * @param pageable pagination and sorting information
   * @return a paginated list of {@link UserDTO}
   */
  @GetMapping
  public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
    return ResponseEntity.ok(userService.getAllUsers(pageable));
  }

  /**
   * Retrieves a single user by ID.
   *
   * @param id the ID of the user
   * @return the {@link UserDTO} for the given ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok().body(userService.getUserById(id));
  }

  /**
   * Updates user details.
   *
   * @param id the ID of the user to update
   * @param request the request payload containing updated fields
   * @return the updated {@link UserDTO}
   */
  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> updateUser(
      @PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
    return ResponseEntity.ok().body(userService.updateUser(id, request));
  }

  /**
   * Deletes a user by ID.
   *
   * @param id the ID of the user to delete
   * @return a 204 No Content response if deletion succeeds
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);

    return ResponseEntity.noContent().build();
  }

  /**
   * Uploads a profile image for the specified user.
   *
   * @param userId the ID of the user
   * @param file the image file to upload
   * @return a 204 No Content response if upload succeeds
   */
  @PostMapping(value = "/{userId}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> uploadUserProfileImage(
      @PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file) {
    userService.uploadProfileImage(userId, file);

    return ResponseEntity.noContent().build();
  }

  /**
   * Retrieves the profile image for the specified user.
   *
   * @param userId the ID of the user
   * @return the profile image as a JPEG byte array
   */
  @GetMapping(value = "/{userId}/profile-image", produces = MediaType.IMAGE_JPEG_VALUE)
  public byte[] getUserProfileImage(@PathVariable("userId") Long userId) {
    return userService.getProfileImage(userId);
  }
}
