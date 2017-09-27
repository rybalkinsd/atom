package ru.atom.geometry;

public class Bar implements Collider/* super class and interfaces here if necessary */ {
    int x1;
    int y1;
    int x2;
    int y2;

    Bar(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isColliding(Collider o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() == o.getClass()) {
            Bar bar = (Bar) o;
            if (((((bar.x1 >= x1) && (bar.x1 <= x2)) || ((bar.x1 >= x2) && (bar.x1 <= x1))) && (((bar.y1 >= y1)
                    && (bar.y1 <= y2)) || ((bar.y1 >= y2) && (bar.y1 <= y1)))) || ((((bar.x2 >= x1) && (bar.x2 <= x2))
                    || ((bar.x2 >= x2) && (bar.x2 <= x1))) && (((bar.y2 >= y1) && (bar.y2 <= y2))
                    || ((bar.y2 >= y2) && (bar.y2 <= y1)))))
                return true;
        } else {
            Point point = (Point) o;
            if ((((point.x >= x1) && (point.x <= x2)) || ((point.x >= x2) && (point.x <= x1))) && (((point.y >= y1)
                    && (point.y <= y2)) || ((point.y >= y2) && (point.y <= y1))))
                return true;
        }
        return false;
    }
    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        if (((bar.x1 == x1) && (bar.y1 == y1) && (bar.x2 == x2) && (bar.y2 == y2)) || ((bar.x1 == x2)
                && (bar.y1 == y2) && (bar.x2 == x1) && (bar.y2 == y1))
                || ((bar.x1 == x2) && (bar.y1 == y1) && (bar.x2 == x1) && (bar.y2 == y2)))
            return true;
        return false;
    }
}
