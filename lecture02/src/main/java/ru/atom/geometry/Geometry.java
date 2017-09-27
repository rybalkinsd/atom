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
        //from bottom left corner to top right corner
        Bar bar = null;
        if (firstCornerX < secondCornerX) {
            if (firstCornerY < secondCornerY) {
                bar = new Bar(firstCornerX, firstCornerY, secondCornerX, secondCornerY);
            } else {
                bar = new Bar(firstCornerX, secondCornerY, secondCornerX, firstCornerY);
            }
        } else {
            if (firstCornerY < secondCornerY) {
                bar = new Bar(secondCornerX, firstCornerY, firstCornerX, secondCornerY);
            } else {
                bar = new Bar(secondCornerX, secondCornerY, firstCornerX, firstCornerY);
            }
        }
        return bar;
    }

    /**
     * 2D point
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        return new Point(x, y);
    }
}
