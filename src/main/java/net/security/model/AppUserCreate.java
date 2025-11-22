package net.security.model;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ToString
@Data
@NoArgsConstructor
public class AppUserCreate implements Serializable {
  @Email private String email;
  @NotBlank private String username;
  @NotBlank private String password;
  @Enumerated
  private UserRole role;

  public AppUserCreate(String email, String username, String password, UserRole role) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
