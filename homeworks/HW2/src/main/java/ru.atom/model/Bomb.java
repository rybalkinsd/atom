package ru.atom.model;

import ru.atom.geometry.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Ксения on 07.03.2017.
 */
public class Bomb implements Temporary, Positionable {

    private int id;
    private int power;
    private Point position;
    private final long lifetime = 3000L;
    private long elapsedTime = 0L;

    public Bomb(int id, int power, Point position) {
        this.id = id;
        this.position = position;
        this.power = power;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;
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
