package net.security.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.security.model.UserRole;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUserEntity implements UserDetails, Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String username;
  private String password;
  private List<UserRole> roles = new ArrayList<>();
  private String createdBy; // USER, SYSTEM

  @DateTimeFormat(pattern = "dd/MMM/yyyyThh:mm:ss")
  private LocalDateTime createdAt;

  private String updatedBy; // USER, SYSTEM

  @DateTimeFormat(pattern = "dd/MMM/yyyyThh:mm:ss")
  private LocalDateTime updatedAt;

  public AppUserEntity(Long id) {
    this.id = id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .toList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }

    public void addRole(UserRole userRole) {
      this.roles.add(userRole);
    }
}
