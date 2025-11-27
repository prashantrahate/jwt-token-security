package net.security.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.security.mappers.MapperUtil;
import net.security.model.FailureResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {

    log.warn(
        "CustomAuthenticationEntryPoint: {} {}",
        authException.getClass(),
        authException.getMessage());

    FailureResponse failureResponse =
        new FailureResponse(
            "Unauthorized user",
            "Access denied: You do not have permission to access this resource.");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
    response.getWriter().write(MapperUtil.objectToJson(failureResponse));
  }
}
