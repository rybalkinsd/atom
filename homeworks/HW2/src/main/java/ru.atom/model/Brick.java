package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Brick implements Tickable, Positionable {

    private static final Logger log = LogManager.getLogger(Brick.class);

    public static Long id = 10000000000L;
    private Point position;

    public Brick(Point position) {

        this.id += 10000;
        this.position = position;
        log.info("New Girl id={}, position={}", id, position);
    }



    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick {}",elapsed);
    }
}
