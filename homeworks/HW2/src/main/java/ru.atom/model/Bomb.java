package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements Positionable, Tickable {

    private int id;
    private int holderId;
    private int currentTimer;
    private int explodeTimer;
    private Point position;
    private boolean isExisting;
    final Logger log = LogManager.getLogger(GameSession.class);

    public Bomb(int id, Point position, int holderId) {
        isExisting = true;
        this.id = id;
        this.position = position;
        this.holderId = holderId;
        log.info("Bomb with ID(" + id + ") was created on point("
                + position.getX() + "," + position.getY() + ")" + " by player("+ holderId + ")");
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
