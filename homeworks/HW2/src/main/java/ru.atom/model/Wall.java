package ru.atom.model;

import ru.atom.geometry.Point;
//import ru.atom.model.GameSession;

/**
 * Created by Даша on 06.03.2017.
 */
public class Wall implements Positionable {

    private Point position;
    private final int id;

    public Wall(int x, int y) {
        this.position = new Point(x, y);
        this.id = GameSession.nextId();
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
