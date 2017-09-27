package ru.atom.geometry;

public class Bar implements Collider {
    private Point firstPoint;
    private Point secondPoint;

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        firstPoint  = new Point(firstCornerX, firstCornerY);
        secondPoint = new Point(secondCornerX, secondCornerY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar)o;

        int squareThis = Math.abs(firstPoint.getX() - secondPoint.getX())
                * Math.abs(firstPoint.getY() - secondPoint.getY());
        int squareOther = Math.abs(bar.getFirstPoint().getX() - bar.getSecondPoint().getX())
                * Math.abs(bar.getFirstPoint().getY() - bar.getSecondPoint().getY());

        return squareThis == squareOther;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point)other;

            int leftX   = Math.min(firstPoint.getX(), secondPoint.getX());
            int leftY   = Math.min(firstPoint.getY(), secondPoint.getY());
            int rightX  = Math.max(firstPoint.getX(), secondPoint.getX());
            int rigthY  = Math.max(firstPoint.getY(), secondPoint.getY());

            return leftX <= point.getX() && point.getX() <= rightX
                    && leftY <= point.getY() && point.getY() <= rigthY;
        } else if (other instanceof Bar) {
            Bar bar = (Bar)other;

            int firstLeftX   = Math.min(firstPoint.getX(), secondPoint.getX());
            int firstLeftY   = Math.min(firstPoint.getY(), secondPoint.getY());
            int firstRightX  = Math.max(firstPoint.getX(), secondPoint.getX());
            int firstRightY  = Math.max(firstPoint.getY(), secondPoint.getY());
            int secondLeftX  = Math.min(bar.getFirstPoint().getX(), bar.getSecondPoint().getX());
            int secondLeftY  = Math.min(bar.getFirstPoint().getY(), bar.getSecondPoint().getY());
            int secondRightX = Math.max(bar.getFirstPoint().getX(), bar.getSecondPoint().getX());
            int secondRightY = Math.max(bar.getFirstPoint().getY(), bar.getSecondPoint().getY());

            boolean crossByComponentX = (firstLeftX <= secondLeftX && secondLeftX <= firstRightX)
                    || (firstLeftX <= secondRightX && secondRightX <= firstRightX);
            boolean crossByComponentY = (firstLeftY <= secondLeftY && secondLeftY <= firstRightY)
                    || (firstLeftY <= secondRightY && secondRightY <= firstRightY);

            return crossByComponentX && crossByComponentY;
        }

        return false;
    }
}