package ru.atom.model;

import ru.atom.geometry.GeomObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Rectangle;
import ru.atom.model.listners.BombExplosListener;
import ru.atom.tick.Tickable;

import java.util.Vector;

public class Bomb extends FormedGameObject implements Tickable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private int explosRadius;
    private long explosDelay;
    Vector<BombExplosListener> listeners = new Vector<>();

    public void addExplosEventListener(BombExplosListener explosEventLisetner) {
        listeners.add(explosEventLisetner);
    }

    public Bomb(GeomObject geomObject, int explosRadius, long explosDelay) {
        super(geomObject);
        this.explosRadius = explosRadius;
        this.explosDelay = explosDelay;
        log.info(this.toString());
    }

    int getExploseRadius() {
        return this.explosRadius;
    }

    void explode() {
        listeners.forEach(bombExplosListener -> {
            bombExplosListener.handleBombExplodeEvent(this);
        });
    }

    @Override
    public void tick(long elapsed) {
        if (explosDelay > 0) {
            explosDelay -= elapsed;
        }

        if (explosDelay <= 0) {
            explode();
        }
    }

    @Override
    public String toString() {
        return "{" + getForm().toString() +
                ",\"id\":" + getId() +
                ",\"type\":\"Bomb\"" +
                "}";
    }

    @Override
    public Rectangle getForm() {
        return (Rectangle)geomObject;
    }
}
