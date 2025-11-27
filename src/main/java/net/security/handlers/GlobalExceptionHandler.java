package net.security.handlers;

import java.util.HashMap;
import java.util.Map;
import net.security.handlers.exceptions.NotFoundException;
import net.security.handlers.exceptions.UnauthorizedUser;
import net.security.handlers.exceptions.ValidationException;
import net.security.model.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
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
    FailureResponse failureResponse = new FailureResponse("Validation failed", new HashMap<>());
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

  @ExceptionHandler(InsufficientAuthenticationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected FailureResponse handleInsufficientAuthenticationException(
      InsufficientAuthenticationException ex) {
    return new FailureResponse(
        "Insufficient authentication: Please provide valid credentials", ex.getMessage());
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected FailureResponse handleAuthenticationException(AuthenticationException ex) {
    return new FailureResponse(
        ex.getMessage(), "Authentication failed: Please provide valid credentials");
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected FailureResponse handleAccessDeniedException(AccessDeniedException ex) {
    return new FailureResponse(
        ex.getMessage(), "Access denied: You do not have permission to access this resource");
  }

  @ExceptionHandler(HttpMessageConversionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected FailureResponse handleHttpMessageConversionException(
      HttpMessageConversionException ex) {
    return new FailureResponse("Required request body is missing or invalid");
  }

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected FailureResponse handleValidationException(ValidationException ex) {
    return new FailureResponse("Validation failed", ex.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected FailureResponse handleNotFoundException(NotFoundException ex) {
    return new FailureResponse("Resource not found", ex.getMessage());
  }
}
