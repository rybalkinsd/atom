package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Obstacle implements Positionable {


    public enum Durability {
        DESTRUCTIBLE, INDESTRUCTIBLE
    }


    private static final Logger log = LogManager.getLogger(Obstacle.class);
    private final int id;
    private Point coordinates;
    private boolean isDestroyed;
    private final Durability type;

    public Obstacle(Point coordinates, Durability type) {
        this.id = GameSession.nextId();
        this.coordinates = coordinates;
        this.type = type;
        isDestroyed = false;
        log.info("Player: id={}, position({}, {}), durability={}\n", id, coordinates.getX(), coordinates.getY(), type);
    }


    @Override
    public Point getPosition() {
        return coordinates;
    }

    @Override
    public int getId() {

        return id;
    }
}
