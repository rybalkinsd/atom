package utils;

import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public class JSONDeserializationException extends Exception {

  public JSONDeserializationException(@NotNull JsonSyntaxException cause) {
    super(cause);
  }
}
