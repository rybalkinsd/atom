package ru.atom.model;

import ru.atom.geometry.Point;

public class Player implements Movable {
    private final int id;

    // Entity position on map grid
    private Point position;

    private long timePassedMillis = 0;

    // Moving speed
    private int velocity = 2;

    // Max number of bombs user can spawn
    private int bombsMax = 1;

    // How far the fire reaches when bomb explodes
    private int bombStrength = 1;

    private boolean alive = true;
    private Bomb[] bombs;

    public Player(Point position) {
        this.id = GameSession.getNextId();
        this.position = position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        timePassedMillis += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        if (direction == Direction.UP) {
            position = new Point(position.getX(), position.getY() + velocity);
        } else if (direction == Direction.DOWN) {
            position = new Point(position.getX(), position.getY() - velocity);
        } else if (direction == Direction.RIGHT) {
            position = new Point(position.getX() + velocity, position.getY());
        } else if (direction == Direction.LEFT) {
            position = new Point(position.getX() - velocity, position.getY());
        }

        return position;
    }
}
