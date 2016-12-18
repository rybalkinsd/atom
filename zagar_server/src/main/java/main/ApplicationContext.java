package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by User on 27.11.2016.
 */
public class ApplicationContext {

    private static final Logger log = LogManager.getLogger(ApplicationContext.class);
    private static final @NotNull Map<Class, Object> contextMap = new ConcurrentHashMap<>();

    private ApplicationContext() {}

    public static void put(@NotNull Class clazz, @NotNull Object object) {
        contextMap.put(clazz, object);
    }

    @NotNull
    public static  <T> T get(@NotNull Class<T> type) {
        return (T) contextMap.get(type);
    }
}
