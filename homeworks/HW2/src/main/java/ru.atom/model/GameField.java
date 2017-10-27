package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameField implements GameObject {
    private static final Logger log = LogManager.getLogger(Wall.class);
    private int id;

    public GameField(int id) {
        this.id = id;
        log.info("Created GameField with id=" + id);
    }

    public int getId() {
        return id;
    }

}
