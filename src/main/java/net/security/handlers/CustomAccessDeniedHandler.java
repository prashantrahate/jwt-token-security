package net.security.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.security.mappers.MapperUtil;
import net.security.model.FailureResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

// @Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {

    log.warn(
        "CustomAccessDeniedHandler: {} {}",
        accessDeniedException.getClass(),
        accessDeniedException.getMessage());

    FailureResponse failureResponse =
        new FailureResponse(
            "Unauthorized user",
            "Access denied: You do not have permission to access this resource.");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write(MapperUtil.objectToJson(failureResponse));
  }
}
