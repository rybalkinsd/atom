package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.IllegalFormatCodePointException;

/**
 * Created by home on 10.03.2017.
 */
public class AbstractGameObject implements Positionable {
    private static final Logger log = LogManager.getLogger(GameSession.class);

    protected final int id;
    private Point position;

    public AbstractGameObject(int x, int y) {
        if (x < 0 || y < 0) {
            log.error("coordinate must be >= 0 ");
            throw new IllegalArgumentException();
        }
        this.id = GameSession.getId();
        this.position = getPosition();
    }

    public Point getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void setPosition(int x, int y) {
        position = new Point(x, y);
    }

    public void setPosition(Point newPosition) {
        position = newPosition;
    }
}
