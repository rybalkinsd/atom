package bomber.games.model;

import bomber.games.geometry.Point;


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
