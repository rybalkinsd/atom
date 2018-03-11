package ru.atom.geometry;

public class Bar implements Collider {
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    Bar(int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY) {
        minX = Math.min(firstCoordX , secondCoordX);
        minY = Math.min(firstCoordY , secondCoordY);
        maxX = Math.max(secondCoordX , firstCoordX);
        maxY = Math.max(secondCoordY , firstCoordY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other == null) return false;
        if (other.getClass() != getClass()) {
            Point ptr = (Point) other;
            return minX <= ptr.getX() && maxX >= ptr.getX() && minY <= ptr.getY() && maxY >= ptr.getY();
        } else {
            if (this == other) return true;

            Bar ptr = (Bar) other;
            return (minX <= ptr.minX && maxX >= ptr.minX || minX <= ptr.maxX && maxX >= ptr.maxX)
                    && (minY <= ptr.minY && maxY >= ptr.minY || minY <= ptr.maxY && maxY >= ptr.maxY);

        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar  ptr = (Bar) o;
        return ptr.maxX == maxX && ptr.maxY == maxY && ptr.minX == minX && ptr.minY == minY;
    }
}
