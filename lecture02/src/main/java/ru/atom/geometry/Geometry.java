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
       Bar b = new Bar();
       Point f = new Point();
       f.setX(firstPointX);
       f.setY(firstCornerY);
       Point s = new Point();
       s.setX(secondCornerX);
       s.setY(secondCornerY);
       b.setL(f);
       b.setR(s);
       return b;

    }

    /**
     * 2D point
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        Point p = new Point();
        p.setX(x);
        p.setY(y);
        return p;
    }
}
