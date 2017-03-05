package ru.atom.geometry;

/**
 * Created by pavel on 02.03.17.
 */
class Bar implements Collider {
    private final int firstPointX;
    private final int firstCornerY;
    private final int secondCornerX;
    private final int secondCornerY;

    Bar(int firestPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = firestPointX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        return this.firstPointX == bar.firstPointX
                && this.firstCornerY == bar.firstCornerY
                && this.secondCornerX == bar.secondCornerX
                && this.secondCornerY == bar.secondCornerY
                || this.firstPointX + this.firstCornerY + this.secondCornerX + this.secondCornerY
                == bar.firstPointX + bar.firstCornerY + bar.secondCornerX + bar.secondCornerY;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point otherPoint = (Point) other;

            return (this.firstPointX <= otherPoint.getX())
                    && (this.firstCornerY <= otherPoint.getY())
                    && (this.secondCornerX >= otherPoint.getX())
                    && (this.secondCornerY >= otherPoint.getY());
        } else {
            Bar otherBar = (Bar) other;

            if (this.equals(otherBar)) {
                return true;
            } else {
                Point pointOne = new Point(otherBar.firstPointX, otherBar.firstCornerY);
                Point pointTwo = new Point(otherBar.secondCornerX, otherBar.secondCornerY);

                return this.isColliding(pointOne) || this.isColliding(pointTwo);
            }
        }
    }
}
