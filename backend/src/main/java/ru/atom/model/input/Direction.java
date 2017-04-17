package ru.atom.model.input;

import ru.atom.util.JsonHelper;
import ru.atom.util.V;

/**
 * Created by sergey on 3/5/17.
 */
public enum Direction {
    UP(V.of(0, 1)),
    DOWN(V.of(0, -1)),
    LEFT(V.of(-1, 0)),
    RIGHT(V.of(1, 0)),
    IDLE(V.ZERO),;

    private final V v;

    Direction(V v) {
        this.v = v;
    }

    public V getVector() {
        return v;
    }
}
