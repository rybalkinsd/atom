package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameField implements GameObject {
    private static final Logger log = LogManager.getLogger(GameField.class);
    private final int id;

    public GameField(int id) {
        this.id = id;
        log.info("GameField with id = " + id);
    }

    @Override
    public int getId() {
        return id;
    }

}