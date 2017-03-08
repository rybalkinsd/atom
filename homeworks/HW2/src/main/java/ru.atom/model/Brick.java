package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Даша on 06.03.2017.
 */
public class Brick implements Positionable, Tickable {

    private Point position;
    private long lifetime;
    private final int id;

    public Brick(int x, int y) {
        this.position = new Point(x, y);
        this.lifetime = 0;
        this.id = GameSession.nextId();
    }

    @Override
    public void tick(long elapsed) {
        lifetime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
