package ru.atom.geometry;

public class Bar implements Collider {

    private Point first;
    private Point second;

    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {

        if (firstCornerX > secondCornerX) {
            int temp = secondCornerX;
            secondCornerX = firstCornerX;
            firstCornerX = temp;
        }

        if (firstCornerY > secondCornerY) {
            int temp = secondCornerY;
            secondCornerY = firstCornerY;
            firstCornerY = temp;
        }

        first = new Point(firstCornerX, firstCornerY);
        second = new Point(secondCornerX, secondCornerY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return other.isColliding(this);
        } else if (other instanceof Bar) {
            if (this.isColliding(((Bar) other).first)
                    || this.isColliding(((Bar) other).second)
                    || other.isColliding(this.first)
                    || other.isColliding(this.second))
                return true;
            else
                return false;
        } else
            return false;
    }

    public Point getFirst() {
        return first;
    }

    public void setFirst(Point first) {
        this.first = first;
    }

    public Point getSecond() {
        return second;
    }

    public void setSecond(Point second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;

        Bar bar = (Bar) o;
        return (this.first.equals(bar.first)) && (this.second.equals(bar.second));
    }
}
