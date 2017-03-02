package ru.atom.geometry;

/**
 * Created by zarina on 02.03.17.
 */
public class Bar implements Collider {
    private Point firstPoint;
    private Point secondPoint;

    public Bar(Point firstPoint, Point secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two bar are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar other = (Bar) o;
        return this.firstPoint.equals(other.firstPoint) && this.secondPoint.equals(other.secondPoint)
            || this.firstPoint.equals(other.secondPoint) && this.secondPoint.equals(other.firstPoint)
            // different X
            || this.secondPoint.getX() == other.firstPoint.getX() && this.secondPoint.getY() == other.secondPoint.getY()
            && this.firstPoint.getX() == other.secondPoint.getX() && this.firstPoint.getY() == other.firstPoint.getY()
            // different Y
            || this.secondPoint.getX() == other.secondPoint.getX() && this.secondPoint.getY() == other.firstPoint.getY()
            && this.firstPoint.getX() == other.firstPoint.getX() && this.firstPoint.getY() == other.secondPoint.getY();
    }

    @Override
    public boolean isColliding(Collider o) {
        if (o instanceof Bar) {
            Bar other = (Bar) o;
            return this.equals(other)
                || this.isPointIntoBar(other.firstPoint) || this.isPointIntoBar(other.secondPoint)
                || other.isPointIntoBar(this.firstPoint) || other.isPointIntoBar(this.secondPoint);
        }
        return (o instanceof Point) && this.isPointIntoBar((Point) o);
    }

    private boolean isPointIntoBar(Point point) {
        return this.firstPoint.getX() <= point.getX() && this.secondPoint.getX() >= point.getX()
            && this.firstPoint.getY() <= point.getY() && this.secondPoint.getY() >= point.getY();
    }
}
