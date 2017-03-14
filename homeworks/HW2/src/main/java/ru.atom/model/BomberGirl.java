package ru.atom.model;

import ru.atom.geometry.Point;

public class BomberGirl implements Movable {
    private final int id;
    private Point position;
    private final int velocity;
    private long passedTime;


    public BomberGirl(Point position, int velocity) {
        if (velocity <= 0) throw new IllegalArgumentException();
        this.id = GameSession.setGameObjectId();
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        passedTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Point move(Direction direction) {
        position = direction.move(position, velocity);
        return position;
    }

    public int getVelocity() {
        return velocity;
    }

    public long getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(long passedTime) {
        this.passedTime = passedTime;
    }
}
