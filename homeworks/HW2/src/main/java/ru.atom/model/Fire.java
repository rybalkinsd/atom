package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Fire implements Tickable, Positionable {

    private static final Logger log = LogManager.getLogger(Fire.class);

    public static Long id = 1000000000000L;
    private Point position;

    public Fire(Point position) {

        this.id += 100000000;
        this.position = position;
        log.info("Fire id={}, position={}", id, position);
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
