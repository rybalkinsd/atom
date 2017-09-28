package ru.atom.geometry;

/**
 * ^ Y
 * |
 * |
 * |
 * |          X
 * .---------->
 */

public final class Geometry {

    private Geometry() {
    }

    /**
     * Bar is a rectangle, which borders are parallel to coordinate axis
     * Like selection bar in desktop, this bar is defined by two opposite corners
     * Bar is not oriented
     * (It is not relevant, which opposite corners you choose to define bar)
     *
     * @return new Bar
     */
    public static Collider createBar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        Bar newBar = new Bar(firstPointX, firstCornerY, secondCornerX, secondCornerY);
        return newBar;
    }

    /**
     * 2D point
     *
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        Point newPoint = new Point(x, y);
        return newPoint;


    }
}
