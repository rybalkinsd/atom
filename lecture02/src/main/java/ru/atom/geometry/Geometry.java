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
    public static Collider createBar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int xmax;
        int xmin;
        int ymax;
        int ymin;
        if (firstCornerX < secondCornerX) {
            xmin = firstCornerX;
            xmax = secondCornerX;
        } else {
            xmin = secondCornerX;
            xmax = firstCornerX;
        }
        if (firstCornerY < secondCornerY) {
            ymin = firstCornerY;
            ymax = secondCornerY;
        } else {
            ymin = secondCornerY;
            ymax = firstCornerY;
        }
        return new Bar(new Point(xmin, ymin), new Point(xmax, ymax));
    }

    /**
     * 2D point
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        return new Point(x, y);
    }
}
