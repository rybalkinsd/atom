package ru.atom.geometry;

public class Bar implements Collider {
    static final int step = 50;
    Point start;
    Point end;

    public Bar(int x1, int y1, int x2, int y2) {
        if (x1 < x2 && y1 < y2) {
            start = new Point(x1, y1);
            end = new Point(x2, y2);
        }
        if (x1 > x2 && y1 < y2) {
            start = new Point(x2, y1);
            end = new Point(x1, y2);
        }
        if (x1 < x2 && y1 > y2) {
            start = new Point(x1, y2);
            end = new Point(x2, y1);
        }
        if (x1 > x2 && y1 > y2) {
            start = new Point(x2, y2);
            end = new Point(x1, y1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return isColliding((Collider) o);

        Bar bar = (Bar) o;

        if (this.start.equals(bar.start) && this.start.equals(bar.end)) {
            return true;
        }
        if (bar.start.x >= this.start.x && bar.start.y >= this.start.y
                && bar.end.x <= this.end.x && bar.end.y <= this.end.y) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        return isIncludes(other) || isIntersects(other);
    }

    public boolean isIncludes(Collider collider) {
        if (collider.getClass() == Bar.class) {
            Bar bar = (Bar) collider;
            if (bar.start.x >= this.start.x && bar.start.y >= this.start.y
                    && bar.end.x <= this.end.x && bar.end.y <= this.end.y) {
                return true;
            }
        }
        if (collider.getClass() == Point.class) {
            Point point = (Point) collider;
            if (point.x >= this.start.x && point.x <= this.end.x
                    && point.y >= this.start.y && point.y <= this.end.y) {
                return true;
            }
        }
        return false;
    }

    public boolean isIntersects(Collider collider) {
        if (collider.getClass() == Bar.class) {
            Bar bar = (Bar) collider;
            for (int x = bar.start.x; x < bar.end.x; x += step) {
                for (int y = bar.start.y; y < bar.end.y; y += step) {
                    if (isIncludes(new Point(x, y))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
