package ru.atom.model;

import javafx.geometry.Pos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;


public class BreakableWall extends AbstractGameObject {

    private static final Logger log = LogManager.getLogger(BreakableWall.class);
    public BreakableWall(Point position) {
        super(position.getX(), position.getY());
        log.info("New breakable wall is set. id={}, x={}, y={}", getId(), position.getX(), position.getY());
    }
 }