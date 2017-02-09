package ru.atom.model.actor.tile;

import ru.atom.model.actor.GameObject;
import ru.atom.util.V;

/**
 * Created by sergey on 2/8/17.
 */
public abstract class Tile extends GameObject {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    public Tile(V position) {
        this.position = position;
    }

    public V getAbsolutePosition() {
        return position.times(WIDTH, HEIGHT);
    }

    public static V toAbsolute(V position) {
        return position.times(WIDTH, HEIGHT);
    }

    public abstract Character getSimpleMapIcon();
}
