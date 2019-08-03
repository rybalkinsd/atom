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
     *
     * @return new Bar
     */
    public static Collider createBar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        try {

        throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
        }
        Collider collider = new Bar();
        ((Bar) collider).setFirstX(firstCornerX);
        ((Bar) collider).setFirstY(firstCornerY);
        ((Bar) collider).setSecondX(secondCornerX);
        ((Bar) collider).setSecondY(secondCornerY);
        return collider;
    }

    /**
     * 2D point
     *
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        try {

        throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
        }
        Collider point = new Point();
        ((Point) point).setX(x);
        ((Point) point).setY(y);
        return point;
    }
}
