package net.security.handlers.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundException extends BaseException{
    private final Logger log = LoggerFactory.getLogger(NotFoundException.class);

    public NotFoundException(String message) {
        super(message);
        log.warn("NotFoundException: {}", message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
        log.warn("NotFoundException: {}", message, cause);
    }
}
