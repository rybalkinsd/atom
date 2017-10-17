package ru.atom.model;

import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;

/**
 * GameObject that has coordinates
 * ^ Y
 * |
 * |
 * |
 * |          X
 * .---------->
 */
public interface Positionable {
    /**
     * @return Current position
     */
    Point getPosition();

    /**
     * @return The space occupied by the object
     */
    Rectangle getSpace();
}
