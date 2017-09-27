package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods

    private int x = 0;
    private int y = 0;

    Point(){}

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;

        // cast from Object to Point
        Point point = (Point) o;
        return (this.x == point.x) && (this.x == point.y);
    }

    @Override
    public boolean isColliding(Collider collider) {
        if (collider instanceof Point)
            return this.equals(collider);
        else if (collider instanceof Bar)
            return (this.x >= ((Bar) collider).getFirst().x)
                    && (this.x <= ((Bar) collider).getSecond().x)
                    && (this.y >= ((Bar) collider).getFirst().y)
                    && (this.y <= ((Bar) collider).getSecond().y);
        else
            return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
