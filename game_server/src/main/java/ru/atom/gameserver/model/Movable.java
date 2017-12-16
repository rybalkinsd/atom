package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.tick.Tickable;

/**
 * Created by Alexandr on 05.12.2017.
 */
public interface Movable extends GameObject, Tickable {

    Point move(Direction direction, long time);

    enum Direction {
        UP, DOWN, RIGHT, LEFT, IDLE
    }
}
