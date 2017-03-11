package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by ilnur on 11.03.17.
 */
public class BombGirl implements Positionable, Movable {
    private Point pos;
    private int id;
    private int speed;

    public BombGirl(int x, int y, int speed) {
        this.pos = new Point(x,y);
        this.id = GameSession.createid();
        this.speed = speed;
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public void tick(long elapsed) {

    }

    /**
     * @return Current position
     */
    @Override
    public Point getPosition() {
        return pos;
    }



    @Override
    public Point move(Movable.Direction direction) {
        switch (direction) {
            case UP: pos = new Point(pos.getX(), pos.getY() + this.speed);
                break;
            case DOWN: pos = new Point(pos.getX(), pos.getY() - this.speed);
                break;
            case LEFT: pos = new Point(pos.getX() - this.speed, pos.getY());
                break;
            case RIGHT: pos = new Point(pos.getX() + this.speed, pos.getY());
                break;
            default: break;
        }
        return pos;
    }
}
