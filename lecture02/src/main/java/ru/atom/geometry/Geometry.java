package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
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
    public static Collider createBar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        Bar bar = new Bar();
        bar.x1 = firstPointX;
        bar.y1 = firstCornerY;
        bar.x2 = secondCornerX;
        bar.y2 = secondCornerY;
        return bar;

        //throw new NotImplementedException();
    }

    /**
     * 2D point
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        Point point = new Point();
        point.x = x;
        point.y = y;
        return point;

        // throw new NotImplementedException();
    }
}
