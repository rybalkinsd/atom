package ru.atom.geometry;

public class Bar implements Collider{
    private Point leftCorner;
    private Point rightCorner;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        Point left = null;
        Point right = null;
        if (firstCornerX < secondCornerX){
            left = new Point(firstCornerX, firstCornerY);
            right = new Point(secondCornerX, secondCornerY);
        }
        else{
            left = new Point(secondCornerX, secondCornerY);
            right = new Point(firstCornerX, firstCornerY);
        }
        this.leftCorner = left;
        this.rightCorner = right;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        return leftCorner.getX() == bar.leftCorner.getX() && rightCorner.getX() == bar.rightCorner.getX() &&
                ((leftCorner.getY() == bar.leftCorner.getY() && rightCorner.getY() == bar.rightCorner.getY()) ||
                (leftCorner.getY() == bar.rightCorner.getY() && rightCorner.getY() == bar.leftCorner.getY()));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other == this) { return true; }
        if (other.getClass() == Point.class) {
            Point point = (Point) other;
            int maxY = Math.max(leftCorner.getY(), rightCorner.getY());
            int minY = Math.min(leftCorner.getY(), rightCorner.getY());
            return leftCorner.getX() <= point.getX() && point.getX() <= rightCorner.getX() &&
                    point.getY() <= maxY && point.getY() >= minY;
        }
        if (other.getClass() == getClass()){
            Bar bar = (Bar) other;
            return !(bar.leftCorner.getX() > rightCorner.getX() || bar.rightCorner.getX() < leftCorner.getX() ||
                    Math.max(bar.leftCorner.getY(), bar.rightCorner.getY()) < Math.min(leftCorner.getY(), rightCorner.getY()) ||
                    Math.min(bar.leftCorner.getY(), bar.rightCorner.getY()) > Math.max(leftCorner.getY(), rightCorner.getY()));
        }
        return false;
    }
}
