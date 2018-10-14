package ru.atom.geometry;

public class Bar implements Collider {

    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = firstCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other == null) return false;
        if (other instanceof Bar) {

            Bar bar = (Bar) other;
            boolean tooLeft =
                    (this.firstCornerX < bar.firstCornerX)
                            && (this.secondCornerX < bar.secondCornerX)
                            && (this.firstCornerX < bar.secondCornerX)
                            && (this.secondCornerX < bar.firstCornerX);

            boolean tooRight = (this.firstCornerX > bar.firstCornerX)
                    && (this.secondCornerX > bar.secondCornerX)
                    && (this.firstCornerX > bar.secondCornerX)
                    && (this.secondCornerX > bar.firstCornerX);

            boolean tooHigh = (this.firstCornerY < bar.firstCornerY)
                    && (this.secondCornerY < bar.secondCornerY)
                    && (this.firstCornerY < bar.secondCornerY)
                    && (this.secondCornerY < bar.firstCornerY);

            boolean tooLow = (this.firstCornerY > bar.firstCornerY)
                    && (this.secondCornerY > bar.secondCornerY)
                    && (this.firstCornerY > bar.secondCornerY)
                    && (this.secondCornerY > bar.firstCornerY);

            return !(tooLeft || tooRight || tooHigh || tooLow);
        }
        if (other instanceof Point) {
            Point point = (Point) other;
            return (
                    point.getX() >= this.firstCornerX
                            && point.getX() <= this.secondCornerX
                            && point.getY() >= this.firstCornerY
                            && point.getY() <= this.secondCornerY);
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;

        if (!(obj instanceof Bar)) return false;

        Bar otherBar = (Bar) obj;

        return (
                this.firstCornerX == otherBar.firstCornerX
                        && this.secondCornerX == otherBar.secondCornerX
                        && this.firstCornerY == otherBar.firstCornerY
                        && this.secondCornerY == otherBar.secondCornerY
                        ||
                        this.firstCornerX == otherBar.secondCornerX
                                && this.secondCornerX == otherBar.firstCornerX
                                && this.firstCornerY == otherBar.firstCornerY
                                && this.secondCornerY == otherBar.secondCornerY
                        ||
                        this.firstCornerX == otherBar.secondCornerX
                                && this.secondCornerX == otherBar.firstCornerX
                                && this.firstCornerY == otherBar.secondCornerY
                                && this.secondCornerY == otherBar.firstCornerY
                        ||
                        this.firstCornerX == otherBar.firstCornerX
                                && this.secondCornerX == otherBar.secondCornerX
                                && this.firstCornerY == otherBar.secondCornerY
                                && this.secondCornerY == otherBar.firstCornerY);


    }
}
