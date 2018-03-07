package ru.atom.geometry;

public class Bar implements Collider {
    Point firstCorner;
    Point secondCorner;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCorner = new Point(firstCornerX, firstCornerY);
        this.secondCorner = new Point(secondCornerX, secondCornerY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        Bar bar1 = new Bar(Math.min(this.firstCorner.x, this.secondCorner.x),
                Math.min(this.firstCorner.y, this.secondCorner.y),
                Math.max(this.firstCorner.x, this.secondCorner.x),
                Math.max(this.firstCorner.y, this.secondCorner.y));
        Bar bar2 = new Bar(Math.min(bar.firstCorner.x, bar.secondCorner.x),
                Math.min(bar.firstCorner.y, bar.secondCorner.y),
                Math.max(bar.firstCorner.x, bar.secondCorner.x),
                Math.max(bar.firstCorner.y, bar.secondCorner.y));

        if ((bar1.firstCorner.x == bar2.firstCorner.x) && (bar1.firstCorner.y == bar2.firstCorner.y)
                && (bar1.secondCorner.x == bar2.secondCorner.x) && (bar1.secondCorner.y == bar2.secondCorner.y))
            return true;
        else
            return false;
    }

    @Override
    public boolean isColliding(Collider o) {
        if (getClass() == o.getClass()) {
            Bar bar = (Bar) o;

            if (this.equals(bar))
                return true;

            Bar bar1 = new Bar(Math.min(this.firstCorner.x, this.secondCorner.x),
                    Math.min(this.firstCorner.y, this.secondCorner.y),
                    Math.max(this.firstCorner.x, this.secondCorner.x),
                    Math.max(this.firstCorner.y, this.secondCorner.y));
            Bar bar2 = new Bar(Math.min(bar.firstCorner.x, bar.secondCorner.x),
                    Math.min(bar.firstCorner.y, bar.secondCorner.y),
                    Math.max(bar.firstCorner.x, bar.secondCorner.x),
                    Math.max(bar.firstCorner.y, bar.secondCorner.y));

            if ((((bar1.firstCorner.x >= bar2.firstCorner.x) && (bar1.firstCorner.x <= bar2.secondCorner.x))
                    || ((bar2.firstCorner.x >= bar1.firstCorner.x) && (bar2.firstCorner.x <= bar1.secondCorner.x)))
                    && (((bar1.firstCorner.y >= bar2.firstCorner.y) && (bar1.firstCorner.y <= bar2.secondCorner.y))
                    || ((bar2.firstCorner.y >= bar1.firstCorner.y) && (bar2.firstCorner.y <= bar1.secondCorner.y))))
                return true;

            else
                return false;
        } else {
            Point point = (Point) o;

            if ((point.x >= Math.min(this.firstCorner.x, this.secondCorner.x))
                    && (point.x <= Math.max(this.firstCorner.x, this.secondCorner.x))
                    && (point.y >= Math.min(this.firstCorner.y, this.secondCorner.y))
                    && (point.y <= Math.max(this.firstCorner.y, this.secondCorner.y)))
                return true;
            else
                return false;
        }
    }
}