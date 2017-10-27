package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SuperBomb extends Bonus {

    private static final Logger log = LogManager.getLogger(SuperBomb.class);

    public SuperBomb() {
        super();
        log.info("superBomb object created");
    }
}
