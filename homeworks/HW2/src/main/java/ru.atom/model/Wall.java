package ru.atom.model;

import ru.atom.geometry.Point;

public class Wall implements Positionable, Tickable {

    private final int id;
    private final Point position;
    private long time=0l;

    public Wall(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void tick(long elapsed) {
        time+=elapsed;
    }


}
