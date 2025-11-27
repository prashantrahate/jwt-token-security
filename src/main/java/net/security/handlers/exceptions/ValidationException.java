package net.security.handlers.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationException extends BaseException {
    private final Logger log = LoggerFactory.getLogger(ValidationException.class);

    public ValidationException(String message) {
        super(message);
        log.warn("ValidationException: {}", message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        log.warn("ValidationException: {}", message, cause);
    }
}
