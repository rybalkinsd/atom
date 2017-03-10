package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by ilysk on 08.03.17.
 */
public class Bonus implements Positionable {

    private final Point position;
    private final int id;

    public Bonus(int x, int y) {
        this.position = new Point(x,y);
        this.id = GameSession.createUniqueId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
