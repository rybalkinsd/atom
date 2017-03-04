package ru.atom.geometry;

/**
 * Created by Areksei on 04.03.17.
 */
public class Bar implements Collider {
    Point p1;
    Point p2;

    Bar(int x1, int y1, int x2, int y2) {
        this.p1 = new Point(Math.min(x1, x2), Math.min(y1, y2));
        this.p2 = new Point(Math.max(x1, x2), Math.max(y1, y2));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            int x1 = this.p1.getX();
            int y1 = this.p1.getY();
            int x2 = this.p2.getX();
            int y2 = this.p2.getY();
            int ox1 = bar.p1.getX();
            int oy1 = bar.p1.getY();
            int ox2 = bar.p2.getX();
            int oy2 = bar.p2.getY();
            return !((x2 < ox1) || (y2 < oy1) || (x1 > ox2) || (y1 > oy2));
        } else if (other instanceof Point) {
            Point point = (Point) other;
            int x1 = this.p1.getX();
            int y1 = this.p1.getY();
            int x2 = this.p2.getX();
            int y2 = this.p2.getY();
            int xp = point.getX();
            int yp = point.getY();
            return (x1 <= xp) && (xp <= x2) && (y1 <= yp) && (yp <= y2);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Bar bar = (Bar) o;
        if (this.p1.equals(bar.p1) && this.p2.equals(bar.p2)) {
            return true;
        }
        return false;
    }
}
