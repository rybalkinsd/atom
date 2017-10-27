package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Boy extends Person {

    private static final Logger log = LogManager.getLogger(Boy.class);

    public Boy() {
        super();
        log.info("boy object created");
    }
}
