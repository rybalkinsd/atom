package utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.jetbrains.annotations.NotNull;
import protocol.model.*;

import java.lang.reflect.Type;

/**
 * @author apomosov
 *
 * Contains helper functions to work with {@see com.google.gson} library
 */
public class JSONHelper {
    private static @NotNull RuntimeTypeAdapterFactory<Cell> cellAdapterFactory =
            RuntimeTypeAdapterFactory.of(Cell.class)
            .registerSubtype(Virus.class)
            .registerSubtype(EjectedMass.class)
            .registerSubtype(Food.class)
            .registerSubtype(PlayerCell.class);

    private static @NotNull Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapterFactory(cellAdapterFactory)
            .create();

    /**
     * Serialize given object
     * @param object object to serialize
     * @return json string
     */
    @NotNull
    public static String toJSON(@NotNull Object object) {
        return gson.toJson(object);
    }

    /**
     * Serialized with given object by his {@link com.google.gson.reflect.TypeToken}
     * @param object object to serialize
     * @param type type token
     * @return json string
     */
    @NotNull
    public static String toJSON(@NotNull Object object, @NotNull Type type) {
        return gson.toJson(object, type);
    }

    /**
     * Deserialize object from JSON
     * @param json JSON string
     * @param type {@link Class<T>} object
     * @param <T> object type parameter
     * @return deserialized object
     * @throws JSONDeserializationException on deserialization error
     */
    @NotNull
    public static <T> T fromJSON(@NotNull String json, @NotNull Class<T> type) throws JSONDeserializationException {
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            throw new JSONDeserializationException(e);
        }
    }

    /**
     * Deserialize object from JSON
     * @param json JSON string
     * @param type {@link Type} object
     * @param <T> object type parameter
     * @return deserialized object
     * @throws JSONDeserializationException on deserialization error
     */
    @NotNull
    public static <T> T fromJSON(@NotNull String json, @NotNull Type type) throws JSONDeserializationException {
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            throw new JSONDeserializationException(e);
        }
    }

    /**
     * Deserialize string to {@link JsonObject}
     * Useful when you need to extract separate fields
     * @param string JSON string
     * @return {@link JsonObject} represents given string
     */
    public static @NotNull JsonObject getJSONObject(@NotNull String string) {
        return gson.fromJson(string, JsonObject.class);
    }
}
