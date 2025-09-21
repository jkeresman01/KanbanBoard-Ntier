package com.keresman.kanbanboard.service.impl;

import com.keresman.kanbanboard.dto.UserDTO;
import com.keresman.kanbanboard.exception.RequestValidationException;
import com.keresman.kanbanboard.exception.ResourceNotFoundException;
import com.keresman.kanbanboard.mapping.UserDTOMapper;
import com.keresman.kanbanboard.model.User;
import com.keresman.kanbanboard.payload.UserUpdateRequest;
import com.keresman.kanbanboard.repository.UserRepository;
import com.keresman.kanbanboard.s3.S3Service;
import com.keresman.kanbanboard.service.UserService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of {@link UserService} providing operations for managing users and their profile
 * images.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserDTOMapper userDTOMapper;
  private final S3Service s3Service;

  /** {@inheritDoc} */
  @Override
  public Page<UserDTO> getAllUsers(Pageable pageable) {
    log.info("Fetching all users with pageable={}", pageable);
    return userRepository.findAll(pageable).map(userDTOMapper);
  }

  /** {@inheritDoc} */
  @Override
  public UserDTO getUserById(Long userId) {
    log.info("Fetching user by id={}", userId);
    return userRepository
        .findById(userId)
        .map(userDTOMapper)
        .orElseThrow(
            () -> new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));
  }

  /** {@inheritDoc} */
  @Override
  public UserDTO updateUser(Long userId, UserUpdateRequest request) {
    log.info("Updating user id={}", userId);

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));

    boolean hasChanges = applyUserUpdates(user, request);

    if (!hasChanges) {
      log.warn("No changes detected in update request for user id={}", userId);
      throw new RequestValidationException("No changes detected in the update request.");
    }

    User updated = userRepository.save(user);
    log.info("Updated user id={} (username={})", updated.getId(), updated.getUsername());

    return userDTOMapper.apply(updated);
  }

  private boolean applyUserUpdates(User user, UserUpdateRequest request) {
    boolean updated = false;
    updated |= updateFirstName(user, request.firstName());
    updated |= updateLastName(user, request.lastName());
    updated |= updateEmail(user, request.email());
    updated |= updateGender(user, request.gender());
    return updated;
  }

  private boolean updateFirstName(User user, String newFirstName) {
    if (isValidUpdate(newFirstName, user.getFirstName())) {
      user.setFirstName(newFirstName);
      return true;
    }
    return false;
  }

  private boolean updateLastName(User user, String newLastName) {
    if (isValidUpdate(newLastName, user.getLastName())) {
      user.setLastName(newLastName);
      return true;
    }
    return false;
  }

  private boolean updateEmail(User user, String newEmail) {
    if (isValidUpdate(newEmail, user.getEmail())) {
      user.setEmail(newEmail);
      return true;
    }
    return false;
  }

  private boolean updateGender(User user, Enum<?> newGender) {
    if (newGender != null && !newGender.equals(user.getGender())) {
      user.setGender((com.keresman.kanbanboard.model.Gender) newGender);
      return true;
    }
    return false;
  }

  private boolean isValidUpdate(String newValue, String oldValue) {
    return newValue != null && !newValue.isBlank() && !newValue.equals(oldValue);
  }

  /** {@inheritDoc} */
  @Override
  public void uploadProfileImage(Long userId, MultipartFile file) {
    log.info("Uploading profile image for user id={}", userId);

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("User not found with id [%s]".formatted(userId)));

    String imageId = UUID.randomUUID().toString();
    String key = "profile-images/%s/%s.jpg".formatted(userId, imageId);

    try {
      s3Service.putObject(key, file.getBytes());
      log.info("Profile image uploaded for user id={} with imageId={}", userId, imageId);
    } catch (IOException e) {
      log.error("Failed to upload profile image for user id={}", userId, e);
      throw new RuntimeException("Failed to upload profile image", e);
    }

    user.setImageId(imageId);
    userRepository.save(user);
  }

  /** {@inheritDoc} */
  @Override
  public byte[] getProfileImage(Long userId) {
    log.info("Fetching profile image for user id={}", userId);

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("User not found with id [%s]".formatted(userId)));

    if (user.getImageId() == null) {
      log.warn("Profile image not set for user id={}", userId);
      throw new ResourceNotFoundException("Profile image not set for user [%s]".formatted(userId));
    }

    String key = "profile-images/%s/%s.jpg".formatted(userId, user.getImageId());
    log.info("Fetching profile image from S3 for user id={} key={}", userId, key);

    return s3Service.getObject(key);
  }

  /** {@inheritDoc} */
  @Override
  public void deleteUser(Long id) {
    log.info("Deleting user id={}", id);

    if (!userRepository.existsById(id)) {
      log.warn("Delete failed — user not found id={}", id);
      throw new ResourceNotFoundException("User with id [%s] not found".formatted(id));
    }

    userRepository.deleteById(id);
    log.info("Deleted user id={}", id);
  }
}
