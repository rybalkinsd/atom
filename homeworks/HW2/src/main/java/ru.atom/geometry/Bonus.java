package ru.atom.geometry;

import ru.atom.model.GameSession;
import ru.atom.model.Positionable;

/**
 * Created by zxcvbg on 22.03.2017.
 */
public class Bonus implements Positionable {
    private Point position;
    private final int id;

    public Bonus(int x, int y) {
        this.position = new Point(x,y);
        this.id = GameSession.getNextId();
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
