package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

/**
 * Created by Fella on 14.03.2017.
 */
public class Bomb implements Temporary, Positionable {

    private final Point position;
    private final int id;
    private long lifetime;
    private int power;
    private static final Logger log = LogManager.getLogger(BomberGirl.class);

    public Bomb(int x, int y, int power) {
        this.position = new Point(x, y);
        this.id = GameSession.createId();
        this.lifetime = 120;
        this.power = power;
        log.info("New Bomb! id=" + id + ", position(" + x + ";" + y + "), power=" + power + ".");
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public void tick(long elapsed) {
        lifetime -= elapsed;
        log.info("tic-tac");

    }

    @Override
    public long getLifetimeMillis() {
        return lifetime;
    }

    @Override
    public boolean isDead() {
        if (lifetime == 0) {
            log.info("Boo-oom!Motherfucker ");
            return true;
        } else if (lifetime < 0) return true;
        else return false;
    }

    @Override
    public Point getPosition() {
        return position;
    }

}
