package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class Brick implements Positionable, Tickable {

    private static final Logger log = LogManager.getLogger(Brick.class);
    private final int id;
    private long lifetime;
    private Point position;

    public Brick(int x, int y) {
        position = new Point(x, y);
        id = GameSession.nextId();
        lifetime = 0L;
        log.info("New Brick id=" + id + ", position=(" + x + "," + y + ")\n");
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
        lifetime += elapsed;
    }
}
