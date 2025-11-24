package net.security.handlers.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnauthorizedUser extends BaseException {
  Logger log = LoggerFactory.getLogger(UnauthorizedUser.class);

  public UnauthorizedUser() {
    log.warn("UnauthorizedUser: User not authorized");
  }

  public UnauthorizedUser(String message) {
    super(message);
    log.warn("UnauthorizedUser: {}", message);
  }

  public UnauthorizedUser(String message, Throwable cause) {
    super(message, cause);
    log.warn("UnauthorizedUser: {}", message);
  }
}
