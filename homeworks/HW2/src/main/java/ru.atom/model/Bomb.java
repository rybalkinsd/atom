package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements Tickable, Positionable {

    private static final Logger log = LogManager.getLogger(Bomb.class);

    public static Long id = 10000000000L;
    private Point position;

    public Bomb(Point position) {

        this.id += 100;
        this.position = position;
        log.info("New Bomb id={}, position={}", id, position);

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
