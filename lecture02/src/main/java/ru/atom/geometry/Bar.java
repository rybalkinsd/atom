package ru.atom.geometry;

/**
 * Template class for Bar
 */
public class Bar implements Collider {
    private Point firstCornerPoint;
    private Point secondCornerPoint;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerPoint = new Point(firstCornerX, firstCornerY);
        this.secondCornerPoint = new Point(secondCornerX, secondCornerY);
    }

    /**
     *   @return first point of Bar
     * */
    public Point getFirstCornerPoint() {
        return firstCornerPoint;
    }

    /**
     *   @return second point of Bar
     * */
    public Point getSecondCornerPoint() {
        return secondCornerPoint;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        return (this.getSecondCornerPoint().getX() == bar.getSecondCornerPoint().getX()
                    || this.getSecondCornerPoint().getX() == bar.getFirstCornerPoint().getX())
                && (this.getSecondCornerPoint().getY() == bar.getSecondCornerPoint().getY()
                    || this.getSecondCornerPoint().getY() == bar.getFirstCornerPoint().getY())
                && (this.getFirstCornerPoint().getX() == bar.getSecondCornerPoint().getX()
                    || this.getFirstCornerPoint().getX() == bar.getFirstCornerPoint().getX())
                && (this.getFirstCornerPoint().getY() == bar.getSecondCornerPoint().getY()
                    || this.getFirstCornerPoint().getY() == bar.getFirstCornerPoint().getY());
    }

    @Override
    public boolean isColliding(Collider other) {
        // for Point
        if (other instanceof Point) {
            Point point = (Point) other;
            return point.getX() >= this.firstCornerPoint.getX()
                    && point.getX() <= this.secondCornerPoint.getX()
                    && point.getY() >= this.firstCornerPoint.getY()
                    && point.getY() <= this.secondCornerPoint.getY();
        } else if (other instanceof Bar) {   // for Bar
            Bar bar = (Bar) other;
            return (bar.getFirstCornerPoint().getX() <= this.getSecondCornerPoint().getX()
                        && bar.getSecondCornerPoint().getX() >= this.getFirstCornerPoint().getX())
                    && (bar.getFirstCornerPoint().getY() <= this.getSecondCornerPoint().getY()
                        && bar.getSecondCornerPoint().getY() >= this.getFirstCornerPoint().getY());
        }

        return false;
    }
}