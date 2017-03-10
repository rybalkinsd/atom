package ru.atom.model;

import ru.atom.geometry.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Ксения on 07.03.2017.
 */
public class Explosion implements Temporary, Positionable {

    private int id;
    private int power;
    private Point position;
    private long elapsedTime = 0L;
    private final long lifetime = 1000L;

    public Explosion(int id, int power, Point position) {
        this.id = id;
        this.power = power;
        this.position = position;
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public long getLifetimeMillis() {
        return lifetime;
    }

    @Override
    public boolean isDead() {

        if (elapsedTime < lifetime) return false;
        else return true;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
