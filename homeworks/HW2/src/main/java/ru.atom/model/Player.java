package ru.atom.model;

import ru.atom.geometry.Point;

public class Player implements Movable {
    private final int id;
    private Point position;
    private boolean boosted;
    private int velocity = 2;
    private int bombsMax = 1;
    private int bombStrength = 1;
    private boolean isDead = false;

    public Player(int x, int y) {
        this.position = new Point(x, y);
        this.id = GameSession.nextId();
    }

    @Override
    public void tick(long elapsed) {
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
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + velocity/* * elapsed*/);
                return position;
            case DOWN:
                position = new Point(position.getX(), position.getY() - velocity/* * elapsed*/);
                return position;
            case RIGHT:
                position = new Point(position.getX() + velocity/* * elapsed*/, position.getY());
                return position;
            case LEFT:
                position = new Point(position.getX() - velocity/* * elapsed*/, position.getY());
                return position;
            case IDLE:
                return position;
            default:
                return position;
        }
    }
}
