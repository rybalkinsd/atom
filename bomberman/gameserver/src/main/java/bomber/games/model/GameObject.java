package bomber.games.model;

import bomber.games.geometry.Point;

/**
 * Any entity of game mechanics
 */
public interface GameObject {
    /**
     * Unique id
     */
    int getId();

    Point getPosition();
}
