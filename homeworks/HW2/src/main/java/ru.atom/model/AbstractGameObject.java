package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;


public abstract class AbstractGameObject implements Positionable {
    private static final Logger logger = LogManager.getLogger(GameSession.class);

    private final int id;
    private Point position;

    public AbstractGameObject(int x, int y) {
        if (x < 0 || y < 0) {
            logger.error("GameObjects x, y must be >= 0!");
            throw new IllegalArgumentException();
        }
        this.id = GameSession.getNextId();
        this.position = new Point(x, y);
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position = new Point(x, y);
    }

    public void setPosition(Point newPosition) {
        position = newPosition;
    }
}
