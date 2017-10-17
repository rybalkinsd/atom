package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SuperFire extends Bonus {

    private static final Logger log = LogManager.getLogger(SuperFire.class);

    public SuperFire() {
        super();
        log.info("superFire object created");
    }


}
