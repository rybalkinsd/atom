package ru.atom.geometry;

public class Bar implements Collider {
    private final int leftX;
    private final int lowerY;
    private final int rightX;
    private final int upperY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {

        if (firstCornerX > secondCornerX) {
            leftX = secondCornerX;
            rightX = firstCornerX;
        } else {
            leftX = firstCornerX;
            rightX = secondCornerX;
        }

        if (firstCornerY > secondCornerY) {
            lowerY = secondCornerY;
            upperY = firstCornerY;
        } else {
            lowerY = firstCornerY;
            upperY = secondCornerY;
        }
    }


    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return (this.leftX <= point.getX()) && (this.rightX >= point.getX()) && (this.lowerY <= point.getY())
                    && (this.upperY >= point.getY());
        }
        if (other instanceof Bar) {
            if (this.equals(other)) return true;
            Bar bar = (Bar) other;
            return this.leftX <= bar.rightX && this.rightX >= bar.leftX
                    && this.upperY >= bar.lowerY && this.lowerY <= bar.upperY;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;
        return (this.rightX == bar.rightX) && (this.leftX == bar.leftX)
                && (this.lowerY == bar.lowerY) && (this.upperY == bar.upperY);
    }
}
