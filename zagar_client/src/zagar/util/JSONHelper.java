package zagar.util;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Base64;

/**
 * @author apomosov
 */
public class JSONHelper {

  @NotNull
  private static final Logger log = LogManager.getLogger(JSONHelper.class);

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

  public static String toSerial( Object o ) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream( baos );
    oos.writeObject( o );
    oos.close();
    return Base64.getEncoder().encodeToString(baos.toByteArray());
  }

  public static Object fromSerial( String s ) throws IOException ,
          ClassNotFoundException {
    try {
      byte[] data = Base64.getDecoder().decode(s);
      ObjectInputStream ois = new ObjectInputStream(
              new ByteArrayInputStream(data));
      Object o = ois.readObject();
      ois.close();
      return o;
    }catch(Exception e){
      log.error("Failed to deserialize object",e);
      return null;
    }
  }

}
