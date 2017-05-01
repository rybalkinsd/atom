package ru.atom.bombergirl.gamemodel.model;

import ru.atom.bombergirl.gamemodel.geometry.Point;

/**
 * Created by dmitriy on 05.03.17.
 */
public class HeavyBlock implements GameObject, Positionable {

    private Point position;
    private final int id;

    public HeavyBlock(int x, int y) {
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
