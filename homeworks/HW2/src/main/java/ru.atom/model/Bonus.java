package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by dmbragin on 3/11/17.
 */
public class Bonus extends PositionalGameObject {
    public Bonus(int x, int y) {
        super(x, y);
    }

    public Bonus(Point position) {
        super(position);
    }
}
