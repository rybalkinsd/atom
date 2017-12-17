package gs.model;

import gs.geometry.Point;
import gs.tick.Tickable;

/**
 * GameObject that can move during game
 */
public interface Movable extends Positionable, Tickable {
    /**
     * Tries to move entity towards specified direction for time
     * @return final position after movement
     */
    Point move(Direction direction, long time);
    
    enum Direction {
        UP, DOWN, RIGHT, LEFT, IDLE
    }
}
