package ru.atom.model;

import ru.atom.geometry.Point;

public class BombGirl implements Movable {
    private final int id;
    private Point position;
    private final int velocity;
    private long passedtime;

    public BombGirl(Point position, int velocity) {
        this.id = GameSession.setGameObjectId();
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public void tick(long tick) {
        this.passedtime += tick;
    }

    @Override
    public Point move(Direction direction) {
        position = direction.move(position, velocity);
        return this.position;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public int getId() {
        return this.id;
    }
}