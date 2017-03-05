package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by gammaker on 05.03.2017.
 */
public class Character extends AbstractGameObject implements Movable {
    private static int SPEED = 5;

    public Character(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {

    }

    @Override
    public Point move(Direction direction) {
        int xpos = pos.getX();
        int ypos = pos.getY();
        switch (direction) {
            case UP:
                ypos += SPEED;
                break;
            case DOWN:
                ypos -= SPEED;
                break;
            case LEFT:
                xpos -= SPEED;
                break;
            case RIGHT:
                xpos += SPEED;
                break;
            default:
        }
        pos = new Point(xpos, ypos);
        return pos;
    }
}
