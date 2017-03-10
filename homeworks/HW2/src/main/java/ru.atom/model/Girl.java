package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by ilysk on 08.03.17.
 */
public class Girl implements Movable {

    private Point position;
    private final int id;
    private final int speed;

    public Girl(int x, int y, int speed) {
        this.position = new Point(x,y);
        this.speed = speed;
        this.id = GameSession.createUniqueId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {}

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP: position = new Point(position.getX(), position.getY() + this.speed);
                break;
            case DOWN: position = new Point(position.getX(), position.getY() - this.speed);
                break;
            case LEFT: position = new Point(position.getX() - this.speed, position.getY());
                break;
            case RIGHT: position = new Point(position.getX() + this.speed, position.getY());
                break;
            default:
                break;
        }
        return position;
    }
}
