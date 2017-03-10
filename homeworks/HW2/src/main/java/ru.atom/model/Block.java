package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Auerbah on 10.03.2017.
 */
public class Block extends PositionableGameObject {
    public Block(Point position) {
        super(position);
    }

    public Block(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + getId() +
                " position=(" + position.getX() + ";" + position.getY() +
                '}';
    }
}
