package ru.atom.lecture08.websocket.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by sergey on 2/2/17.
 */
public final class JsonHelper {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
    }

    @NotNull
    public static String toJson(@NotNull Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static <T> T fromJson(@NotNull String json, @NotNull Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static JsonNode getJsonNode(@NotNull String json) {
        return fromJson(json, JsonNode.class);
    }

    private JsonHelper() {
    }
}