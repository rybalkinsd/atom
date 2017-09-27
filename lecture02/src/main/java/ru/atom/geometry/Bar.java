package ru.atom.geometry;

/**
 * Template class for
 */
public class Bar implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods
    private Point lbc;
    private int w;
    private int h;

    public Bar(Point p1, Point p2) {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        this.w = Math.abs(dx);
        this.h = Math.abs(dy);

        if (dx < 0 && dy < 0) {
            this.lbc = new Point(p1);
        }
        if (dx < 0 && dy > 0) {
            this.lbc = new Point(p1.x, p1.y - dy);
        }
        if (dx > 0 && dy < 0) {
            this.lbc = new Point(p2.x, p2.y + dy);
        }
        if (dx > 0 && dy > 0) {
            this.lbc = new Point(p2);
        }
    }

    public Bar(Bar that) {
        lbc = new Point(that.lbc);
        w = that.w;
        h = that.h;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // cast from Object to Point
        Bar bar = (Bar) o;
        if (lbc.equals(bar.lbc) && w == (bar.w) && h == bar.h) {

            return true;
        } else {
            return false;
        }

    }

    public boolean isColliding(Collider other) {

        if (other.getClass() == Bar.class) {
            Bar extBar = new Bar((Bar) other);
            extBar.lbc.x -= w;
            extBar.lbc.y -= h;
            extBar.w += w;
            extBar.h += h;

            if (extBar.isColliding(this.lbc)) {
                return true;
            } else {
                return false;
            }

        } else if (other.getClass() == Point.class) {

            Point point = (Point)other;
            if ((this.lbc.x <= point.x && point.x <= this.lbc.x + this.w) 
                && (this.lbc.y <= point.y && point.y <= this.lbc.y + this.h)) {
                return true;
            } else {
                return false;
            }

        } else
            return false;
    }
}
