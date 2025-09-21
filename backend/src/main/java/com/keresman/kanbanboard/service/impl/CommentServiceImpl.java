package com.keresman.kanbanboard.service.impl;

import com.keresman.kanbanboard.dto.CommentDTO;
import com.keresman.kanbanboard.exception.RequestValidationException;
import com.keresman.kanbanboard.exception.ResourceNotFoundException;
import com.keresman.kanbanboard.mapping.CommentDTOMapper;
import com.keresman.kanbanboard.model.Comment;
import com.keresman.kanbanboard.model.Task;
import com.keresman.kanbanboard.payload.CommentCreateRequest;
import com.keresman.kanbanboard.payload.CommentUpdateRequest;
import com.keresman.kanbanboard.repository.CommentRepository;
import com.keresman.kanbanboard.repository.TaskRepository;
import com.keresman.kanbanboard.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link CommentService} that manages CRUD operations for comments associated
 * with tasks.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final TaskRepository taskRepository;
  private final CommentDTOMapper mapper;

  /** {@inheritDoc} */
  @Override
  public List<CommentDTO> getCommentsForTask(Long taskId) {
    log.info("Fetching comments for taskId={}", taskId);
    Task task =
        taskRepository
            .findById(taskId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Task with id [%s] not found".formatted(taskId)));

    List<CommentDTO> comments = commentRepository.findAllByTask(task).stream().map(mapper).toList();

    log.info("Fetched {} comments for taskId={}", comments.size(), taskId);
    return comments;
  }

  /** {@inheritDoc} */
  @Override
  public CommentDTO getCommentById(Long id) {
    log.info("Fetching comment by id={}", id);
    return commentRepository
        .findById(id)
        .map(mapper)
        .orElseThrow(
            () -> new ResourceNotFoundException("Comment with id [%s] not found".formatted(id)));
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public CommentDTO createComment(CommentCreateRequest request) {
    log.info(
        "Creating new comment for taskId={} by authorUserId={}",
        request.taskId(),
        request.authorUserId());

    Task task =
        taskRepository
            .findById(request.taskId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Task with id [%s] not found".formatted(request.taskId())));

    Comment comment =
        Comment.builder()
            .task(task)
            .authorUserId(request.authorUserId())
            .message(request.message())
            .build();

    Comment saved = commentRepository.save(comment);
    log.info("Created comment id={} for taskId={}", saved.getId(), request.taskId());

    return mapper.apply(saved);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public CommentDTO updateComment(Long id, CommentUpdateRequest request) {
    log.info("Updating comment id={}", id);

    Comment comment =
        commentRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Comment with id [%s] not found".formatted(id)));

    if (request.message().equals(comment.getMessage())) {
      log.warn("No changes detected for comment id={}", id);
      throw new RequestValidationException("No changes detected in the update request.");
    }

    comment.setMessage(request.message());
    Comment updated = commentRepository.save(comment);

    log.info("Updated comment id={}", id);
    return mapper.apply(updated);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public void deleteComment(Long id) {
    log.info("Deleting comment id={}", id);

    if (!commentRepository.existsById(id)) {
      log.warn("Delete failed — comment not found id={}", id);
      throw new ResourceNotFoundException("Comment with id [%s] not found".formatted(id));
    }

    commentRepository.deleteById(id);
    log.info("Deleted comment id={}", id);
  }
}
