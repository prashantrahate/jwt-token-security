package net.security.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Data
@NoArgsConstructor
public class AppUser implements Serializable {
  private Long id;
  @Email private String email;
  @NotBlank private String username;
  @NotBlank private String password;
  private String role;
  private String createdBy; // USER, SYSTEM

  @DateTimeFormat(pattern = "dd/MMM/yyyyThh:mm:ss")
  private LocalDateTime createdAt;

  private String updatedBy; // USER, SYSTEM

  @DateTimeFormat(pattern = "dd/MMM/yyyyThh:mm:ss")
  private LocalDateTime updatedAt;

  public AppUser(String email, String username, String password, String role) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
    this.createdAt = LocalDateTime.now();
  }
}
