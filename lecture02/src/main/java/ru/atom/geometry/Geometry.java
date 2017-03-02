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
    public static Collider createBar(int firstPointX, int firstPointY, int secondPointX, int secondPointY) {
        Collider bar = new Bar(new Point(firstPointX, firstPointY), new Point(secondPointX, secondPointY));
        return bar;
    }

    /**
     * 2D point
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        Collider point = new Point(x, y);
        return point;
    }
}
