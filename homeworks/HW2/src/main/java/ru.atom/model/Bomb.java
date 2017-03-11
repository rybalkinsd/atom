package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by dmbragin on 3/11/17.
 */
public class Bomb extends TemporaryGameObject {
    public Bomb(int x, int y, long lifeTime) {
        super(x, y, lifeTime);
    }

    public Bomb(Point position, long lifeTime) {
        super(position, lifeTime);
    }
}
