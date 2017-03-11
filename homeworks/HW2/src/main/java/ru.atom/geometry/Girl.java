package ru.atom.geometry;

import ru.atom.model.GameSession;
import ru.atom.model.GameObject;
import ru.atom.model.Movable;

public class Girl implements GameObject, Movable {
    private Point position;
    private Direction direction = Direction.IDLE;
    private int step = 1;
    private final int id;

    public Girl (int x, int y) {
        this.position = new Point (x, y);
        id = GameSession.getNextId();
    }

    @Override
    public int getId () {
        return id;
    }

    @Override
    public Point getPosition () {
        return position;
    }

    @Override
    public void tick (long elapsed) {
        move(direction);
    }

    @Override
    public Point move (Direction direction) {
        switch (direction) {
            case UP:
                position = new Point (position.getX(), position.getY() + step);
                break;
            case DOWN:
                position = new Point (position.getX(), position.getY() - step);
                break;
            case LEFT:
                position = new Point (position.getX() - step, position.getY());
                break;
            case RIGHT:
                position = new Point (position.getX() + step, position.getY());
                break;
            default:
                break;
        }
        return position;
    }
}