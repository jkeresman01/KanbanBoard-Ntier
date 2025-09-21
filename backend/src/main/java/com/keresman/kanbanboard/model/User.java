package com.keresman.kanbanboard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(name = "uq_username", columnNames = "username"),
      @UniqueConstraint(name = "uq_email", columnNames = "email"),
      @UniqueConstraint(name = "uq_image_id", columnNames = "image_id")
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "username", nullable = false, unique = true, columnDefinition = "TEXT")
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false, unique = true, columnDefinition = "TEXT")
  private String email;

  @Column(name = "first_name", nullable = false, length = 120, columnDefinition = "TEXT")
  private String firstName;

  @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", nullable = false, columnDefinition = "TEXT")
  private Gender gender;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, columnDefinition = "TEXT")
  private Role role;

  @Column(name = "image_id", nullable = false, unique = true)
  private String imageId;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
