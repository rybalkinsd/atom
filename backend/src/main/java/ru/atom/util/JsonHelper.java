package ru.atom.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * Created by sergey on 2/2/17.
 */
public final class JsonHelper {
    private static Gson gson = new GsonBuilder().create();

    @NotNull
    public static String toJSON(@NotNull Object object) {
        return gson.toJson(object);
    }

    @NotNull
    public static <T> T fromJSON(@NotNull String json, @NotNull Class<T> type) {
        return gson.fromJson(json, type);
    }

    @NotNull
    public static JsonObject getJSONObject(@NotNull String string) {
        return gson.fromJson(string, JsonObject.class);
    }

    private JsonHelper () { }
}