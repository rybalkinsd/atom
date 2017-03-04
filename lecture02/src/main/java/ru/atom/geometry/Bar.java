package ru.atom.geometry;

/**
 * Created by Даша on 01.03.2017.
 */
public class Bar implements Collider {
    //fields
    //private int firstPointX;
    //private int firstCornerY;
    //private int secondCornerX;
    //private int secondCornerY;
    private Point firstPoint;
    private Point secondPoint;

    //methods
    public Bar() {
    }

    public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        if (firstPointX > secondCornerX) {
            int tmp = firstPointX;
            firstPointX = secondCornerX;
            secondCornerX = tmp;
        }
        if (firstCornerY > secondCornerY) {
            int tmp = firstCornerY;
            firstCornerY = secondCornerY;
            secondCornerY = tmp;
        }
        this.firstPoint = new Point(firstPointX, firstCornerY);
        this.secondPoint = new Point(secondCornerX, secondCornerY);
    }

    public boolean isColliding(Collider other) {
        if (other.getClass() == getClass()) {
            Bar bar = (Bar) other;
            return ((firstPoint.getX() <= bar.firstPoint.getX()) && (secondPoint.getX() >= bar.firstPoint.getX())
                    || (firstPoint.getX() <= bar.secondPoint.getX()) && (secondPoint.getX() >= bar.secondPoint.getX()))
                    && ((firstPoint.getY() <= bar.firstPoint.getY()) && (secondPoint.getY() >= bar.firstPoint.getY())
                    || (firstPoint.getY() <= bar.secondPoint.getY()) && (secondPoint.getY() >= bar.secondPoint.getY()));
        } else if (other.getClass() == Point.class) {
            Point point = (Point) other;
            return (firstPoint.getX() <= point.getX()) && (secondPoint.getX() >= point.getX())
                    && (firstPoint.getY() <= point.getY()) && (secondPoint.getY() >= point.getY());
        } else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;
        return (firstPoint.equals(bar.firstPoint)) && (secondPoint.equals(bar.secondPoint));
    }
}
