package zagar.util;

import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public class JSONHelper {
  @NotNull
  private static Gson gson = new GsonBuilder().create();

  @NotNull
  public static String toJSON(@NotNull Object object) {
    return gson.toJson(object);
  }

  @NotNull
  public static <T> T fromJSON(@NotNull String json, @NotNull Class<T> type) throws JSONDeserializationException {
    try {
      return gson.fromJson(json, type);
    } catch (JsonSyntaxException e) {
      throw new JSONDeserializationException(e);
    }
  }

  @NotNull
  public static JsonObject getJSONObject(@NotNull String string) {
    return gson.fromJson(string, JsonObject.class);
  }
}
