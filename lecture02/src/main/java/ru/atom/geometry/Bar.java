package ru.atom.geometry;

public class Bar implements Collider {

    private Point vertex1 = new Point(0, 0);
    private Point vertex3 = new Point(1, 1);

    public Bar(int x1, int y1, int x3, int y3) {
        this.vertex1 = new Point(min(x1, x3), min(y1, y3));
        this.vertex3 = new Point(max(x1, x3), max(y1, y3));
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int min(int a, int b) {
        return a < b ? a : b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar other = (Bar) o;
        return vertex1.equals(other.vertex1) && vertex3.equals(other.vertex3);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar obj = (Bar) other;
            return (obj.vertex1.moreOrEquals(this.vertex1) && obj.vertex1.lessOrEquals(this.vertex3))
                    || (obj.vertex3.moreOrEquals(this.vertex1) && obj.vertex3.lessOrEquals(this.vertex3));

        } else if (other instanceof Point) {
            Point obj = (Point) other;
            return obj.moreOrEquals(this.vertex1) && obj.lessOrEquals(this.vertex3);
        }
        return true;
    }

}
