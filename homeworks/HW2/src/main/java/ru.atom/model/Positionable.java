package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * GameObject that has coordinates
 * ^ Y
 * |
 * |
 * |
 * |          X
 * .---------->
 */
public interface Positionable extends GameObject {
    /**
     * @return Current position
     */
    Point getPosition();
}
