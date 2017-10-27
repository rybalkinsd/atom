package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wall extends MapBlock {

    private static final Logger log = LogManager.getLogger(Wall.class);

    public Wall() {
        super();
        log.info("wall object created");
    }
}
