package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by dmbragin on 3/11/17.
 */
public class Fire extends TemporaryGameObject {
    public Fire(int x, int y, long lifeTime) {
        super(x, y, lifeTime);
    }

    public Fire(Point position, long lifeTime) {
        super(position, lifeTime);
    }
}
