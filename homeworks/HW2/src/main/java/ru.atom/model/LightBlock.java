package ru.atom.model;

import ru.atom.geometry.Point;
import ru.atom.model.GameObject;
import ru.atom.model.GameSession;
import ru.atom.model.Positionable;

/**
 * Created by dmitriy on 05.03.17.
 */
public class LightBlock implements GameObject, Positionable {

    private Point position;
    private final int id;

    public LightBlock(int x, int y) {
        this.position = new Point(x, y);
        id = GameSession.nextValue();
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
