package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * это разрушаемая коробка
 * Created by ilnur on 11.03.17.
 */
public class Block implements Positionable {
    private int id;
    private Point pos;

    /**
     * Unique id
     */

    public Block(int x, int y) {
        this.pos = new Point(x,y);
        this.id = GameSession.createid();
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @return Current position
     */

    @Override
    public Point getPosition() {
        return null;
    }
}
