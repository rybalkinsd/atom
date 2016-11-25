package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * https://shipilev.net/blog/archive/safe-publication/
 *
 * @author apomosov
 */
public class ApplicationContext {
  private static final @NotNull Logger log = LogManager.getLogger(ApplicationContext.class);
  private static volatile @Nullable ApplicationContext instance;

  public static @NotNull ApplicationContext instance() {
    if (instance == null) {
      synchronized (ApplicationContext.class) {
        if (instance == null) {
          instance = new ApplicationContext();
        }
      }
    }
    return instance;
  }

  private final @NotNull Map<Class, Object> contextMap = new ConcurrentHashMap<>();

  public void put(@NotNull Class clazz, @NotNull Object object) {
    contextMap.put(clazz, object);
  }

  @NotNull
  public <T> T get(@NotNull Class<T> type) {
    return (T) contextMap.get(type);
  }

  private ApplicationContext() {
    log.info(ApplicationContext.class.getName() + " initialized");
  }
}