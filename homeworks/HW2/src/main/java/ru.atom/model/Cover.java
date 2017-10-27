package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cover extends MapBlock {

    private static final Logger log = LogManager.getLogger(Cover.class);

    public Cover() {
        super();
        log.info("cover object created");
    }
}
