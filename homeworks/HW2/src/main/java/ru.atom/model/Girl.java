package ru.atom.model;

import ru.atom.geometry.Point;

public class Girl implements Movable {
    private int id;
    private Point position;
    private int step; //скорость

    public Girl(int id, Point position) {
        this.id = id;
        this.position = position;
        step = 1;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP: position = new Point(position.getX(), position.getY() + step);
            break;
            case DOWN: position = new Point(position.getX(), position.getY() - step);
            break;
            case LEFT: position = new Point(position.getX() - step, position.getY());
            break;
            case RIGHT: position = new Point(position.getX() + step, position.getY());
            break;
            case IDLE: default: break;
        }
        return position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void tick(long elapsed) {

    }

    @Override
    public int getId() {
        return id;
    }

}
