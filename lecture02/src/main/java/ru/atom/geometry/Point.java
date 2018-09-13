package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider{
    private int X;
    private int Y;

    public  Point(){
        X = 0;
        Y = 0;

    }

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }


    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        //throw new UnsupportedOperationException();
        if (this.X == point.X &&
            this.Y == point.Y) return true;
        else return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        return equals(other);
    }
}
