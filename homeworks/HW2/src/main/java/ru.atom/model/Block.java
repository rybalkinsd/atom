package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Block implements GameObject {
    private static final Logger log = LogManager.getLogger(Girl.class);

    private Point position;
    private int id;

    public Block(int id, Point pos) {
        this.id = id;
        this.position = pos;
        log.info("Created new Block: id = {} x = {} y = {}", id, pos.getX(), pos.getY());
    }

    public Point getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }
}