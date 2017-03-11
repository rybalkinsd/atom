package ru.atom.model;

import ru.atom.geometry.Point;

public class WoodBox extends PositionableObject {

    public WoodBox(Point pos) {
        this.setPosition(pos);
    }

    public boolean isDead(boolean bool) {
        return bool;
    }

}
