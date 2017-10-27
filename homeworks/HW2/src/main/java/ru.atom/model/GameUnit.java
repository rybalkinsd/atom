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
        int posX = position.getX();
        int posY = position.getY();
        switch (direction) {
            case UP:
                posY += speed * time;
                break;
            case DOWN:
                posY -= speed * time;
                break;
            case RIGHT:
                posX += speed * time;
                break;
            case LEFT:
                posX -= speed * time;
                break;
            default:
        }
        position = new Point(posX, posY);
        return position;
    }

    @Override
    public void tick(long elapsed) {

    }
}
