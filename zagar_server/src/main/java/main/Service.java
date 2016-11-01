package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by apomosov on 15.05.16.
 */
public abstract class Service extends Thread {
  @NotNull
  private static final Logger log = LogManager.getLogger(Service.class);

  public Service(@NotNull String name) {
    super(name);
    if (log.isInfoEnabled()) {
      log.info("AccountServer thread [" + name + "] created");
    }
  }
}
