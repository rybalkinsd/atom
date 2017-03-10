package ru.atom.model;

import javafx.geometry.Pos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by home on 10.03.2017.
 */
public class BreakableWall extends AbstractGameObject {
    private static final Logger log = LogManager.getLogger(BreakableWall.class);

    public BreakableWall(Point position) {
        super(position.getX(), position.getY());
        log.info("New breakable wall was set id={}, x={}, y={}", getId(), position.getX(), position.getY());
    }
}
