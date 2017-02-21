package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import messageSystem.Address;

/**
 * Created by User on 26.11.2016.
 */
public abstract class Service extends Thread{

    private static final Logger log = LogManager.getLogger(Service.class);
    @NotNull
    private final Address address;

    public Service(@NotNull String name) {
        super(name);
        this.address = new Address(name);
        if (log.isInfoEnabled()) {
            log.info("Service thread [" + name + "] created");
        }
    }

    @NotNull
    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address.toString();
    }

}
