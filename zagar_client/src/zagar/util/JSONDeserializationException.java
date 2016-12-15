package zagar.util;

import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;

public class JSONDeserializationException extends Exception {

  public JSONDeserializationException(@NotNull JsonSyntaxException cause) {
    super(cause);
  }
}