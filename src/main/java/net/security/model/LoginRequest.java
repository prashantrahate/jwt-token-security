package net.security.model;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest implements Serializable {
  @NotBlank private String username;
  @NotBlank private String password;
}
