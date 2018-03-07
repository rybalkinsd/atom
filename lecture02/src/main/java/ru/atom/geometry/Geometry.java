package ru.atom.geometry;

/**
 *  ^ Y
 *  |
 *  |
 *  |
 *  |          X
 *  .---------->
 */

public final class Geometry {
    
    private Geometry() {
    }

    /**
     * Bar is a rectangle, which borders are parallel to coordinate axis
     * Like selection bar in desktop, this bar is defined by two opposite corners
     * Bar is not oriented
     * (It is not relevant, which opposite corners you choose to define bar)
     * @return new Bar
     */

    public static Collider createBar(int x1, int y1, int x2, int y2) {
        return new Bar(x1, y1, x2, y2);
    }
    /**
     * 2D point
     * @return new Point
     */

    public static Collider createPoint(int x, int y) {
        return new Point(x, y);
    }
}
