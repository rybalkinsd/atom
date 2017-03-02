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
        Point far = new Point();
        far.setX(firstPointX);
        far.setY(firstCornerY);

        Point so = new Point();
        so.setX(secondCornerX);
        so.setY(secondCornerY);

        Bar bar = new Bar();
        bar.setL(far);
        bar.setR(so);
        return bar;
    }

    /**
     * 2D point
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        return point;
    }
}
