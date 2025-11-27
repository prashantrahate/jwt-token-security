package net.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FailureResponse(String message, String error, Map<String, Object> errors)
    implements Serializable {

  public FailureResponse(String message) {
    this(message, null, null);
  }

  public FailureResponse(String message, String error) {
    this(message, error, null);
  }

  public FailureResponse(String message, Map<String, Object> errors) {
    this(message, null, errors);
  }

  public void addError(String key, Object value) {
    errors.put(key, value);
  }
}
