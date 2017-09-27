package ru.atom.geometry;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = Math.min(firstCornerX, secondCornerX);
        this.firstCornerY = Math.min(firstCornerY, secondCornerY);
        this.secondCornerX = Math.max(firstCornerX, secondCornerX);
        this.secondCornerY = Math.max(firstCornerY, secondCornerY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return (point.getX() >= firstCornerX && point.getX() <= secondCornerX && point.getY() >= firstCornerY
                    && point.getY() <= secondCornerY);
        } else {
            Bar bar = (Bar) other;
            return ((((firstCornerX >= bar.firstCornerX && firstCornerX <= bar.secondCornerX)
                    || (secondCornerX >= bar.firstCornerX && secondCornerX <= bar.secondCornerX))
                    && ((firstCornerY >= bar.firstCornerY && firstCornerY <= bar.secondCornerY)
                    || (secondCornerY >= bar.firstCornerY && secondCornerY <= bar.secondCornerY)))
                    || (((bar.firstCornerX >= firstCornerX && bar.firstCornerX <= secondCornerX)
                    || (bar.secondCornerX >= firstCornerX && bar.secondCornerX <= secondCornerX))
                    && ((bar.firstCornerY >= firstCornerY && bar.firstCornerY <= secondCornerY)
                    || (bar.secondCornerY >= firstCornerY && bar.secondCornerY <= secondCornerY))))
                    || ((((firstCornerX >= bar.firstCornerX && firstCornerX <= bar.secondCornerX)
                    || (secondCornerX >= bar.firstCornerX && secondCornerX <= bar.secondCornerX))
                    && ((bar.firstCornerY >= firstCornerY && bar.firstCornerY <= secondCornerY)
                    || (bar.secondCornerY >= firstCornerY && bar.secondCornerY <= secondCornerY)))
                    || (((bar.firstCornerX >= firstCornerX && bar.firstCornerX <= secondCornerX)
                    || (bar.secondCornerX >= firstCornerX && bar.secondCornerX <= secondCornerX))
                    && ((firstCornerY >= bar.firstCornerY && firstCornerY <= bar.secondCornerY)
                    || (secondCornerY >= bar.firstCornerY && secondCornerY <= bar.secondCornerY))));
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        return firstCornerX == bar.firstCornerX && firstCornerY == bar.firstCornerY
                && secondCornerX == bar.secondCornerX && secondCornerY == bar.secondCornerY;
    }
}
