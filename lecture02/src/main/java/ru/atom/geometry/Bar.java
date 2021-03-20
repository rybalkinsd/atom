package ru.atom.geometry;

/**
 * Template class for
 */
public class Bar implements Collider /* super class and interfaces here if necessary */ {
    private Point P_1;
    private Point P_2;
    private float Height;
    private float Width;

    // and methods
    Bar(Point p1, Point p2) {
        P_1 = p1;
        P_2 = p2;
        Height = Math.max(p1.Get_Y(), p2.Get_Y()) - Math.min(p1.Get_Y(), p2.Get_Y());
        Width = Math.max(p1.Get_X(), p2.Get_X()) - Math.min(p1.Get_X(), p2.Get_X());
    }

    public Point Get_another_low_point() {
        Point point = P_1.Get_Y() >= P_2.Get_Y() ? P_1 : P_2;
        return new Point((int)point.Get_X(), (int)(point.Get_Y() - Height));
    }
    public Point Get_another_high_point() {
        Point point = P_1.Get_Y() <= P_2.Get_Y() ? P_1 : P_2;
        return new Point((int)point.Get_X(), (int)(point.Get_Y() + Height));
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
        Bar bar = (Bar) o;

        // your code here
        return
                Math.abs( bar.P_2.Get_X() - bar.P_1.Get_X()) - Math.abs(this.P_2.Get_X() - this.P_1.Get_X()) < 10e-5 &&
                Math.abs(bar.P_2.Get_Y() - bar.P_1.Get_Y()) - Math.abs(this.P_2.Get_Y() - this.P_1.Get_Y()) < 10e-5;
    }

    public boolean Contains_point(Point point) {
        return
        point.Get_X() >= Math.min(this.P_1.Get_X(), this.P_2.Get_X()) &&
        point.Get_X() <= Math.max(this.P_1.Get_X(), this.P_2.Get_X()) &&
        point.Get_Y() >= Math.min(this.P_1.Get_Y(), this.P_2.Get_Y()) &&
        point.Get_Y() <= Math.max(this.P_1.Get_Y(), this.P_2.Get_Y());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == this.getClass()) {
            Bar bar = (Bar)other;
            Point this_high_pt = Get_another_high_point();
            Point this_low_pt = Get_another_low_point();
            Point other_high_pt = bar.Get_another_high_point();
            Point other_low_pt = bar.Get_another_low_point();

            return
            this.Contains_point(bar.P_1) || this.Contains_point(bar.P_2) ||
            this.Contains_point(other_high_pt) || this.Contains_point(other_low_pt) ||
            (this_high_pt.Get_Y() >= other_high_pt.Get_Y() &&
                    this_high_pt.Get_X() >= Math.min(other_high_pt.Get_X(), other_low_pt.Get_X()) &&
                    this_high_pt.Get_X() <= Math.max(other_high_pt.Get_X(), other_low_pt.Get_X()) &&
            this_low_pt.Get_Y() <= other_low_pt.Get_Y() &&
                    this_low_pt.Get_X() >= Math.min(other_high_pt.Get_X(), other_low_pt.Get_X()) &&
                    this_low_pt.Get_X() <= Math.max(other_high_pt.Get_X(), other_low_pt.Get_X())
                    ) ||
            (this_high_pt.Get_Y() <= other_high_pt.Get_Y() &&
                    other_high_pt.Get_X() >= Math.min(this_high_pt.Get_X(), this_low_pt.Get_X()) &&
                    other_high_pt.Get_X() <= Math.max(this_high_pt.Get_X(), this_low_pt.Get_X()) &&
            this_low_pt.Get_Y() >= other_low_pt.Get_Y() &&
                    other_low_pt.Get_X() >= Math.min(this_high_pt.Get_X(), this_low_pt.Get_X()) &&
                    other_low_pt.Get_X() <= Math.max(this_high_pt.Get_X(), this_low_pt.Get_X())
            ) ||
            bar.Contains_point(this_high_pt) || bar.Contains_point(this_low_pt) ||
            bar.Contains_point(P_1) || bar.Contains_point(P_2);
        }
        else if (other.getClass() == Point.class){
            Point point = (Point)other;
            return this.Contains_point(point);
        } else return false;
    }
}
