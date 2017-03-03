package ru.atom.geometry;


/**
 * Template class for
 */
public class Point implements Collider {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
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

        return point.x == this.x && point.y == this.y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        }
        if (other instanceof Bar) {
            return (((Bar) other).getLowerLeftX() <= this.x && this.x <= ((Bar) other).getUpperRightX() &&
                    ((Bar) other).getLowerLeftY() <= this.y && this.y <= ((Bar) other).getUpperRightY());
        }
        return false;
    }
}
