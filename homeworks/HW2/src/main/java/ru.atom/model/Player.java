package ru.atom.model;

import ru.atom.geometry.Point;

public class Player implements Movable {
    private final int id;
    private Point position;
    private boolean boosted;
    private int velocity = 2;
    private int bombsMax;
    private int bombStrength;
    private boolean isDead = false;
    private long lifetime;

    public Player(int x, int y) {
        this.position = new Point(x, y);
        this.id = GameSession.nextId();
    }

    @Override
    public void tick(long elapsed) {
        lifetime += elapsed;
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
                position = new Point(position.getX(), position.getY() + velocity);
                return position;
            case DOWN:
                position = new Point(position.getX(), position.getY() - velocity);
                return position;
            case RIGHT:
                position = new Point(position.getX() + velocity, position.getY());
                return position;
            case LEFT:
                position = new Point(position.getX() - velocity, position.getY());
                return position;
            case IDLE:
                return position;
            default:
                return position;
        }
    }
}
