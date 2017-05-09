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

    /**
     * Bar is a rectangle, which borders are parallel to coordinate axis
     * Like selection bar in desktop, this bar is defined by two opposite corners
     * Bar is not oriented
     * (It is not relevant, which opposite corners you choose to define bar)
     * @return new Bar
     */
    public static Collider createBar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        Point far = new Point();
        far.setPoint(firstPointX,firstCornerY);
        Point so = new Point();
        so.setPoint(secondCornerX,secondCornerY);

        Bar bar = new Bar();
        bar.setBar(far,so);
        return bar;
    }

    /**
     * 2D point
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        Point point = new Point();
        point.setPoint(x,y);
        return point;
    }
}
