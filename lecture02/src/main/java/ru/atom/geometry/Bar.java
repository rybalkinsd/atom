package ru.atom.geometry;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public int getFirstCornerX() {
        return firstCornerX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    public void setFirstCornerX(int firstCornerX) {
        this.firstCornerX = firstCornerX;
    }

    public void setFirstCornerY(int firstCornerY) {
        this.firstCornerY = firstCornerY;
    }

    public void setSecondCornerX(int secondCornerX) {
        this.secondCornerX = secondCornerX;
    }

    public void setSecondCornerY(int secondCornerY) {
        this.secondCornerY = secondCornerY;
    }

    public Bar() {
        firstCornerX = firstCornerY = secondCornerX = secondCornerY = 0;
    }

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = Math.min(firstCornerX, secondCornerX);
        this.firstCornerY = Math.min(firstCornerY, secondCornerY);

        this.secondCornerX = Math.max(firstCornerX, secondCornerX);
        this.secondCornerY = Math.max(firstCornerY, secondCornerY);
    }

    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return firstCornerX == bar.getFirstCornerX() && firstCornerY == bar.getFirstCornerY()
                && secondCornerX == bar.getSecondCornerX() && secondCornerY == bar.getSecondCornerY();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (getClass() != other.getClass()) {
            Point point = (Point) other;
            return firstCornerX <= point.getX() && point.getX() <= secondCornerX
                    && firstCornerY <= point.getY() && point.getY() <= secondCornerY;
        } else {
            Bar bar = (Bar) other;
            return !(firstCornerX > bar.secondCornerX || secondCornerX < bar.firstCornerX
                    || secondCornerY < bar.firstCornerY || firstCornerY > bar.secondCornerY);
        }
    }
}
