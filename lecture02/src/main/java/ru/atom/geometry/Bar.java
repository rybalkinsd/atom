package ru.atom.geometry;

import java.util.Objects;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = Math.min(firstCornerX, secondCornerX);
        this.firstCornerY = Math.min(firstCornerY, secondCornerY);
        this.secondCornerX = Math.max(firstCornerX, secondCornerX);
        this.secondCornerY = Math.max(firstCornerY, secondCornerY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return  (point.getX() >= firstCornerX
                    && point.getX() <= secondCornerX
                    && point.getY() >= firstCornerY
                    && point.getY() <= secondCornerY);

        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (bar.firstCornerX > secondCornerX
                    || bar.firstCornerY > secondCornerY
                    || bar.secondCornerX < firstCornerX
                    || bar.secondCornerY < firstCornerY) {
                return false;
            }
            return true;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return firstCornerX == bar.firstCornerX
                && firstCornerY == bar.firstCornerY
                && secondCornerX == bar.secondCornerX
                && secondCornerY == bar.secondCornerY;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(firstCornerX, firstCornerY, secondCornerX, secondCornerY);
    }
}

