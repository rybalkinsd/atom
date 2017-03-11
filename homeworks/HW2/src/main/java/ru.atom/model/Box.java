package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by dmbragin on 3/11/17.
 */
public class Box extends PositionalGameObject {
    public Box(int x, int y) {
        super(x, y);
    }

    public Box(Point position) {
        super(position);
    }
}
