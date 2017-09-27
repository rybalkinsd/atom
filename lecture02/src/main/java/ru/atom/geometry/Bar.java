package ru.atom.geometry;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return Math.max(firstCornerX,secondCornerX) == Math.max(bar.firstCornerX,bar.secondCornerX)
                && Math.max(firstCornerY,secondCornerY) == Math.max(bar.firstCornerY,bar.secondCornerY)
                && Math.min(firstCornerX,secondCornerX) == Math.min(bar.firstCornerX,bar.secondCornerX)
                && Math.min(firstCornerY,secondCornerY) == Math.min(bar.firstCornerY,bar.secondCornerY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() == other.getClass()) {
            Bar bar = (Bar) other;
            return (Math.max(firstCornerX, secondCornerX) <= Math.max(bar.firstCornerX, bar.secondCornerX)
                    && Math.max(firstCornerX, secondCornerX) >= Math.min(bar.firstCornerX, bar.secondCornerX)
                    || Math.min(firstCornerX, secondCornerX) <= Math.max(bar.firstCornerX, bar.secondCornerX)
                    && Math.max(firstCornerX, secondCornerX) >= Math.max(bar.firstCornerX, bar.secondCornerX))
                    && (Math.max(firstCornerY, secondCornerY) <= Math.max(bar.firstCornerY, bar.secondCornerY)
                    && Math.max(firstCornerY, secondCornerY) >= Math.min(bar.firstCornerY, bar.secondCornerY)
                    || Math.min(firstCornerY, secondCornerY) <= Math.max(bar.firstCornerY, bar.secondCornerY)
                    && Math.max(firstCornerY, secondCornerY) >= Math.max(bar.firstCornerY, bar.secondCornerY));
        } else {
            Point point = (Point) other;
            return Math.max(firstCornerX, secondCornerX) >= point.x
                    && Math.min(firstCornerX, secondCornerX) <= point.x
                    && Math.min(firstCornerY, secondCornerY) <= point.y
                    && Math.max(firstCornerY, secondCornerY) >= point.y;
        }
    }

    public Bar(int firstCornerX,int firstCornerY,int secondCornerX,int secondCornerY) {
        this.firstCornerX = firstCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }
}
