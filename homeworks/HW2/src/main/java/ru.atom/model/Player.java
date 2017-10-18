package ru.atom.model;

import ru.atom.geometry.Point;
import ru.atom.geometry.Rect;

public class Player implements Movable {
    Point position;
    Rect rec;
    static final int speed = 1;
    //размеры игрока
    public static final int HEIGHT = 10;
    public static final int WIDTH = 10;

    public Player(Point pos) {
        this.position = pos;
        setRect();
    }

    private void setRect() {
        Point v1 = new Point(position.getX() - WIDTH / 2, position.getY() - HEIGHT / 2);
        Point v2 = new Point(position.getX() + WIDTH / 2, position.getY() + HEIGHT / 2);
        this.rec = new Rect(v1, v2);
    }

    public Point getPosition() {
        return position;
    }

    public Rect getRect() {
        return rec;
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
        setRect();

        return position;
    }

    public int getId() {
        return 100;
    }
}