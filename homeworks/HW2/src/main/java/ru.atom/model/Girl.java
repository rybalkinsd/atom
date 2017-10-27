package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Girl extends Person {

    private static final Logger log = LogManager.getLogger(Girl.class);

    public Girl() {
        super();
        log.info("girl object created");
    }

}
