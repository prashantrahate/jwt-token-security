package net.security.handlers;

import java.util.Map;
import net.security.handlers.exceptions.UnauthorizedUser;
import net.security.model.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected FailureResponse handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    FailureResponse failureResponse = new FailureResponse("Validation failed", Map.of());
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            fieldError -> {
              failureResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
            });
    return failureResponse;
  }

  @ExceptionHandler(UnauthorizedUser.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected FailureResponse handleUnauthorizedUser(UnauthorizedUser ex) {
    return new FailureResponse(
        "This user is not authorized to call this endpoint", ex.getMessage());
  }
}
