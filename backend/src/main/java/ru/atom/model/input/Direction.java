package ru.atom.model.input;

import ru.atom.util.V;

/**
 * Created by sergey on 3/5/17.
 */
public enum Direction {
    Up(V.of(0, 1)),
    Down(V.of(0, -1)),
    Left(V.of(-1, 0)),
    Right(V.of(1, 0)),
    Idle(V.ZERO),;

    private final V v;

    Direction(V v) {
        this.v = v;
    }

    public V getVector() {
        return v;
    }
}
