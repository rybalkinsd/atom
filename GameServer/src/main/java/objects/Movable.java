package objects;

import geometry.Point;

public interface Movable extends Positionable, Tickable {
    /**
     * Tries to move entity towards specified direction for time
     * @return final position after movement
     */
    Point move(String direction, long time);
}
