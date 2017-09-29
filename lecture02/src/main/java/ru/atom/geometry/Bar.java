package ru.atom.geometry;

public class Bar implements Collider {
    public int x1;
    public int x2;
    public int y1;
    public int y2;


    public Bar(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }


    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        Bar bar = (Bar) o;
        if ((bar.x1 == x1 || bar.x1 == x2) && (bar.x2 == x2 || bar.x2 == x1) &&
                (bar.y1 == y1 || bar.y1 == y2) && (bar.y2 == y1 || bar.y2 == y2)) return true;
        else return false;


    }

    public boolean isColliding(Collider other) {

        if (other instanceof Point) {
            Point point = (Point) other;
            if (x1 <= point.x && point.x <= x2 && y1 <= point.y && point.y <= y2) return true;
        } else {
            Bar bar = (Bar) other;
            if (((x1 <= bar.x1 && bar.x1 <= x2) || (x1 <= bar.x2 && bar.x2 <= x2)) &&
                    ((y1 <= bar.y1 && bar.y1 <= y2) || (y1 <= bar.y2 && bar.y2 <= y2))) return true;
            if (bar.x1 < x1 && x2 < bar.x2 && bar.y1 < y1 && y2 < bar.y2) return true;
        }

        return false;

    }

}
