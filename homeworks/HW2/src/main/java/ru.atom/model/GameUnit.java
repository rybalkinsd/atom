package ru.atom.model;

import ru.atom.geometry.Point;

public class GameUnit extends GameObjectAbstract implements Movable {
    private int speed = 10;

    public GameUnit(int x, int y) {
        super(x, y);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public Point move(Direction direction, long time) {
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case UP:
                y += speed * time;
                break;
            case DOWN:
                y -= speed * time;
                break;
            case RIGHT:
                x += speed * time;
                break;
            case LEFT:
                x -= speed * time;
                break;
            default:
        }
        position = new Point(x, y);
        return position;
    }

    @Override
    public void tick(long elapsed) {

    }
}
