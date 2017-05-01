package ru.atom.bombergirl.gamemodel.model;

import ru.atom.bombergirl.gamemodel.geometry.Point;

/**
 * GameObject that can move during game
 */
public interface Movable extends Positionable, Tickable {
    /**
     * Tries to move entity towards specified direction
     * @return final position after movement
     */
    Point move(Direction direction);
    
    enum Direction {
        UP, DOWN, RIGHT, LEFT, IDLE
    }
}
