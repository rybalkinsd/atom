package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    protected int x;
    protected int y;
    private String flag;
public Point(){
}
    public static Collider createPoint(int x, int y) {
        Collider point=new Point();
        ((Point) point).x=x;
        ((Point) point).y=y;
        ((Point) point).flag="point";
        return point;
    }
    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
            public boolean equals(Object o) {
        Point point = (Point) o;

        if (this.x == point.x) {
            if (this.y == point.y) {
                return true;
            } else return false;
        } else return false;
    }
    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other))
            return true;
        else return false;
    }
}
