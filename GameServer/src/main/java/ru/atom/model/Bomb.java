package ru.atom.model;

import ru.atom.geometry.Collider;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class Bomb extends FormedGameObject implements Tickable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private int explosRadius;
    private long explosDelay;

    public Bomb(GeomObject geomObject, int explosRadius, long explosDelay) {
        super(geomObject);
        this.explosRadius = explosRadius;
        this.explosDelay = explosDelay;
        log.info("Created: [id = " + this.getId() + "] Bomb( " + geomObject.toString() +
                ", explosRadius = " + explosRadius +
                ", explosDelay = " + explosDelay +
                " )");
    }

    int getExploseRadius() {
        return explosRadius;
    }

    void explode() { }

    @Override
    public void tick(long elapsed) {
        if (explosDelay > 0) {
            explosDelay -= elapsed;
        }

        if (explosDelay <= 0) {
            explode();
        }
    }
}
