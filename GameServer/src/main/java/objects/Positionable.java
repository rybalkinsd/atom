package objects;

import geometry.Point;

/**
 * GameObject that has coordinates
 */
public interface Positionable extends GameObject {
    /**
     * @return Current position
     */
    Point getPosition();
}
