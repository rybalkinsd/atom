package ru.atom.model;

import ru.atom.geometry.Point;

public class Player implements Movable {
    Point position;
    static final int speed = 1;

    public Player(Point pos) {
        this.position = pos;
    }

    public Point getPosition() {
        return position;
    }

    public void tick(long time) {

    }

    public Point move(Direction d, long time) {
        switch (d) {
            case DOWN: position = new Point(position.getX(), position.getY() - (int) time * speed);
            break;
            case UP: position = new Point(position.getX(), position.getY() + (int) time * speed);
            break;
            case LEFT: position = new Point(position.getX() - (int) time * speed, position.getY());
            break;
            case RIGHT: position = new Point(position.getX() + (int) time * speed, position.getY());
            break;
            default:
                break;
        }
        return position;
    }

    public int getId() {
        return 100;
    }
}