package ru.atom.model;

import ru.atom.geometry.Point;

public class Player implements Movable {

    private Point position;
    private final int id;
    private long elapsedTime;
    private int velocity = 0;
    private int bombPower;
    private boolean isDead = false;

    public Player(int x, int y) {
        position = new Point(x, y);
        id = GameSession.idCounter();
        velocity = 1;
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
