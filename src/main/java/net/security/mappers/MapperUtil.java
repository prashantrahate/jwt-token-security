package net.security.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.security.handlers.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperUtil {
  private static final Logger log = LoggerFactory.getLogger(MapperUtil.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String objectToJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      log.error("Error converting object to JSON", e);
      throw new BaseException("Error converting object to JSON");
    }
  }
}
