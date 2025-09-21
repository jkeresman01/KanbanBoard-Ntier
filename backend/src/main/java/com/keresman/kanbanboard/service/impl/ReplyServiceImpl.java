package com.keresman.kanbanboard.service.impl;

import com.keresman.kanbanboard.dto.ReplyDTO;
import com.keresman.kanbanboard.exception.RequestValidationException;
import com.keresman.kanbanboard.exception.ResourceNotFoundException;
import com.keresman.kanbanboard.mapping.ReplyDTOMapper;
import com.keresman.kanbanboard.model.Comment;
import com.keresman.kanbanboard.model.Reply;
import com.keresman.kanbanboard.payload.ReplyCreateRequest;
import com.keresman.kanbanboard.payload.ReplyUpdateRequest;
import com.keresman.kanbanboard.repository.CommentRepository;
import com.keresman.kanbanboard.repository.ReplyRepository;
import com.keresman.kanbanboard.service.ReplyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link ReplyService} that provides CRUD operations for replies associated with
 * comments.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService {

  private final ReplyRepository replyRepository;
  private final CommentRepository commentRepository;
  private final ReplyDTOMapper mapper;

  /** {@inheritDoc} */
  @Override
  public List<ReplyDTO> getRepliesForComment(Long commentId) {
    log.info("Fetching replies for commentId={}", commentId);

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Comment with id [%s] not found".formatted(commentId)));

    List<ReplyDTO> replies =
        replyRepository.findAllByComment(comment).stream().map(mapper).toList();

    log.info("Fetched {} replies for commentId={}", replies.size(), commentId);
    return replies;
  }

  /** {@inheritDoc} */
  @Override
  public ReplyDTO getReplyById(Long id) {
    log.info("Fetching reply by id={}", id);

    return replyRepository
        .findById(id)
        .map(mapper)
        .orElseThrow(
            () -> new ResourceNotFoundException("Reply with id [%s] not found".formatted(id)));
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public ReplyDTO createReply(ReplyCreateRequest request) {
    log.info(
        "Creating reply for commentId={} by authorUserId={}",
        request.commentId(),
        request.authorUserId());

    Comment comment =
        commentRepository
            .findById(request.commentId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Comment with id [%s] not found".formatted(request.commentId())));

    Reply reply =
        Reply.builder()
            .comment(comment)
            .authorUserId(request.authorUserId())
            .message(request.message())
            .build();

    Reply saved = replyRepository.save(reply);
    log.info("Created reply id={} for commentId={}", saved.getId(), request.commentId());

    return mapper.apply(saved);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public ReplyDTO updateReply(Long id, ReplyUpdateRequest request) {
    log.info("Updating reply id={}", id);

    Reply reply =
        replyRepository
            .findById(id)
            .orElseThrow(
                new ResourceNotFoundException("Reply with id [%s] not found".formatted(id)));

    if (request.message().equals(reply.getMessage())) {
      log.warn("No changes detected for reply id={}", id);
      throw new RequestValidationException("No changes detected in the update request.");
    }

    reply.setMessage(request.message());
    Reply updated = replyRepository.save(reply);

    log.info("Updated reply id={}", id);
    return mapper.apply(updated);
  }

  /** {@inheritDoc} */
  @Transactional
  @Override
  public void deleteReply(Long id) {
    log.info("Deleting reply id={}", id);

    if (!replyRepository.existsById(id)) {
      log.warn("Delete failed — reply not found id={}", id);
      throw new ResourceNotFoundException("Reply with id [%s] not found".formatted(id));
    }

    replyRepository.deleteById(id);
    log.info("Deleted reply id={}", id);
  }
}
