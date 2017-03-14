package ru.atom.model;

import ru.atom.geometry.Collider;
import ru.atom.geometry.Point;

/**
 * Created by Fella on 14.03.2017.
 */
public class Nishtyaki implements Positionable {
    Type type;
    private Point position;
    private int id;

    public Nishtyaki(int x, int y, Type type) {
        this.position = new Point(x, y);
        this.id = GameSession.createId();
        this.type = type;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public enum Type {
        SPEED,
        POWER,
        NUMBER_BOMB
    }
}
