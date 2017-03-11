package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Юля on 10.03.2017.
 */
public class Block extends ObjectPosition {
    public Block(int x, int y) {
        super();
        setPosition(new Point(x, y));
    }
}
