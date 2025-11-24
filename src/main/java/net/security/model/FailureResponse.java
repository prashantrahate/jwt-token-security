package net.security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FailureResponse(String message, String error, Map<String, Object> errors) implements Serializable {
  public FailureResponse(String message, String error) {
    this(message, error, Map.of());
  }

  public FailureResponse(String message, Map<String, Object> errors) {
    this(message, null, errors);
  }

  public void addError(String key, Object value) {
    errors.put(key, value);
  }
}
