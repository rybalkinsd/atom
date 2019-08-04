package ru.atom.geometry;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

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
     *
     * @return new Bar
     */
    public static Collider createBar(int firstX, int firstY, int secondX, int secondY) {

        Collider collider = new Bar();

        ((Bar) collider).setDownLeftPoint((Point)createPoint(min(firstX,secondX),min(firstY,secondY)));
        ((Bar) collider).setUpRightPoint((Point)createPoint(max(firstX,secondX),max(firstY,secondY)));
        return collider;
    }

    /**
     * 2D point
     *
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        
        Collider point = new Point();

        ((Point) point).setX(x);
        ((Point) point).setY(y);
        return point;
    }
}
