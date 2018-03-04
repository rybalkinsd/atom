package ru.atom.geometry;

public class Bar implements Collider {


    //private Point rtp = new Point ();
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int x1, int y1, int x2, int y2) {
        if (x1 < x2) {
            this.firstCornerX = x1;
            this.secondCornerX = x2;
        } else {
            this.firstCornerX = x2;
            this.secondCornerX = x1;
        }
        if (y1 < y2) {
            this.firstCornerY = y1;
            this.secondCornerY = y2;
        } else {
            this.firstCornerY = y2;
            this.secondCornerY = y1;
        }

    }

    public boolean isColliding(Collider other) {

        if (other instanceof Bar)
            return other instanceof Collider && (this.equals(other) || this.intersects(other));
        else if (other instanceof Point)
            return other instanceof Collider && this.pointOnBar(other);
        else
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        return firstCornerX == bar.firstCornerX && firstCornerY == bar.firstCornerY
                && secondCornerX == bar.secondCornerX && secondCornerY == bar.secondCornerY;
    }

    public boolean pointOnBar(Object o) {
        Point point = (Point) o;
        return firstCornerX <= point.getX() && firstCornerY <= point.getY()
                && secondCornerY >= point.getX() && secondCornerY >= point.getY();
    }

    public boolean intersects(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        return !((firstCornerX > bar.secondCornerX || firstCornerY > bar.secondCornerY)
                || (secondCornerX < bar.firstCornerX || secondCornerY < bar.firstCornerY));
    }

}
