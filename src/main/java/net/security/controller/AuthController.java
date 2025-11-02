package net.security.controller;

import jakarta.validation.Valid;
import net.security.model.AppUser;
import net.security.model.LoginRequest;
import net.security.model.Token;
import net.security.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  // /register
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AppUser registerUser(@Valid @RequestBody AppUser user) {
    return authService.registerUser(user);
  }

  // /token
  @PostMapping("/token")
  @ResponseStatus(HttpStatus.CREATED)
  public Token authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return authService.generateToken(loginRequest);
  }
}
