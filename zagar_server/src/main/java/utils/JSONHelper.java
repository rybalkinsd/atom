package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Base64;

/**
 * @author apomosov
 */
public class JSONHelper {
  private static @NotNull Gson gson = new GsonBuilder().create();

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

  public static @NotNull JsonObject getJSONObject(@NotNull String string) {
    return gson.fromJson(string, JsonObject.class);
  }

  public static String toSerial( Object o ) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream( baos );
    oos.writeObject( o );
    oos.close();
    return Base64.getEncoder().encodeToString(baos.toByteArray());
  }

  public static Object fromSerial( String s ) throws IOException ,
          ClassNotFoundException {
    byte [] data = Base64.getDecoder().decode( s );
    ObjectInputStream ois = new ObjectInputStream(
            new ByteArrayInputStream(  data ) );
    Object o  = ois.readObject();
    ois.close();
    return o;
  }
}
