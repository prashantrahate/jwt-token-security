package net.security.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.security.handlers.exceptions.UnauthorizedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {

    log.warn("CustomAccessDeniedHandler: {}", accessDeniedException.getMessage());

    throw new UnauthorizedUser(
        "Access denied: You do not have permission to access this resource.",
        accessDeniedException.getCause());
  }
}
