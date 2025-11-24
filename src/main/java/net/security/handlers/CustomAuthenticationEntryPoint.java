package net.security.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.security.handlers.exceptions.UnauthorizedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {

    log.warn("CustomAuthenticationEntryPoint: {}", authException.getMessage());

    throw new UnauthorizedUser(
        "Access denied: You do not have permission to access this resource.",
        authException.getCause());
  }
}
