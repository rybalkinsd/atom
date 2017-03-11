package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by dmbragin on 3/11/17.
 */
public class Wall extends PositionalGameObject {
    public Wall(int x, int y) {
        super(x, y);
    }

    public Wall(Point position) {
        super(position);
    }
}
