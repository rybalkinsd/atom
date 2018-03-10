package ru.atom.geometry;

public class Bar implements Collider {
    private Point firstCorner;
    private Point secondCorner;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCorner = new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY, secondCornerY));
        this.secondCorner = new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY, secondCornerY));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if ((this.firstCorner.getX() == bar.firstCorner.getX())
                && (this.firstCorner.getY() == bar.firstCorner.getY())
                && (this.secondCorner.getX() == bar.secondCorner.getX())
                && (this.secondCorner.getY() == bar.secondCorner.getY()))
            return true;
        else
            return false;
    }

    @Override
    public boolean isColliding(Collider o) {
        if (o instanceof Bar) {
            Bar bar = (Bar) o;

            if (this.equals(bar))
                return true;

            if ((((this.firstCorner.getX() >= bar.firstCorner.getX())
                    && (this.firstCorner.getX() <= bar.secondCorner.getX()))
                    || ((bar.firstCorner.getX() >= this.firstCorner.getX())
                    && (bar.firstCorner.getX() <= this.secondCorner.getX())))
                    && (((this.firstCorner.getY() >= bar.firstCorner.getY())
                    && (this.firstCorner.getY() <= bar.secondCorner.getY()))
                    || ((bar.firstCorner.getY() >= this.firstCorner.getY())
                    && (bar.firstCorner.getY() <= this.secondCorner.getY()))))
                return true;

            else
                return false;
        } else if (o instanceof Point) {
            Point point = (Point) o;

            if ((point.getX() >= this.firstCorner.getX()) && (point.getX() <= this.secondCorner.getX())
                    && (point.getY() >= this.firstCorner.getY()) && (point.getY() <= this.secondCorner.getY()))
                return true;
            else
                return false;
        }
        return false;
    }
}