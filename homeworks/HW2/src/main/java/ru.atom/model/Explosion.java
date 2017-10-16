package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Explosion implements Positionable, Tickable {

    private int id;
    private Point position;
    private boolean isExisting;
    private int duration;
    final Logger log = LogManager.getLogger(GameSession.class);

    public Explosion(int id, Point position) {
        isExisting = true;
        this.id = id;
        this.position = position;
        log.info("Explosion with ID(" + id + ") was created on point("
                + position.getX() + "," + position.getY() + ")");
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
    }
}
