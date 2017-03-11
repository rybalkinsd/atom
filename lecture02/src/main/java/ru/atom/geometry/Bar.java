package ru.atom.geometry;

/**
 * Created by Areksei on 04.03.17.
 */
public class Bar implements Collider {
    private Point p1;
    private Point p2;

    Bar(int x1, int y1, int x2, int y2) {
        this.p1 = new Point(Math.min(x1, x2), Math.min(y1, y2));
        this.p2 = new Point(Math.max(x1, x2), Math.max(y1, y2));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return p1.isLeft(bar.p2) && p1.isLower(bar.p2)
                    && p2.isRight(bar.p1) && p2.isHigher(bar.p1);
        } else if (other instanceof Point) {
            Point point = (Point) other;
            return p1.isLeft(point) && p1.isLower(point)
                    && p2.isRight(point) && p2.isHigher(point);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Bar bar = (Bar) o;
        return this.p1.equals(bar.p1) && this.p2.equals(bar.p2);
    }
}
