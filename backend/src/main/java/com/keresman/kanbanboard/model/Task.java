package com.keresman.kanbanboard.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false, length = 160)
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  @Builder.Default
  private Status status = Status.BACKLOG;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "task_labels", joinColumns = @JoinColumn(name = "task_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "label")
  @Builder.Default
  private Set<Label> labels = new HashSet<>();

  @Column(name = "creator_user_id", nullable = false)
  private Long creatorUserId;

  @Column(name = "assignee_user_id")
  private Long assigneeUserId;

  @Column(name = "due_at")
  private Instant dueAt;

  @Column(name = "position")
  private Double position;

  @Column(name = "create_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @PrePersist
  void prePersist() {
    Instant now = Instant.now();
    createdAt = now;
    updatedAt = now;
    if (position == null) {
      position = now.toEpochMilli() / 1000.0;
    }
  }

  @PreUpdate
  void preUpdate() {
    updatedAt = Instant.now();
  }
}
