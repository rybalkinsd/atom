package main;

import messageSystem.Abonent;
import messageSystem.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by apomosov on 15.05.16.
 */
public abstract class Service extends Thread implements Abonent {
  @NotNull
  private static final Logger log = LogManager.getLogger(Service.class);
  @NotNull
  private final Address address;

  public Service(@NotNull String name) {
    super(name);
    this.address = new Address(name);
    if (log.isInfoEnabled()) {
      log.info("AccountServer thread [" + name + "] created");
    }
  }

  @NotNull
  @Override
  public Address getAddress() {
    return address;
  }

  @Override
  public String toString() {
    return address.toString();
  }
}
