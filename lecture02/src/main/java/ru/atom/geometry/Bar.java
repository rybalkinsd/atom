package ru.atom.geometry;

public class Bar implements Collider {

    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;

    public Bar(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }


    @Override
    public boolean isColliding(Collider other) {

        //sorting
        int thisTop = this.getY1() > this.getY2() ? this.getY1() : this.getY2();
        int thisBottom = this.getY1() < this.getY2() ? this.getY1() : this.getY2();
        int thisLeft = this.getX1() < this.getX2() ? this.getX1() : this.getX2();
        int thisRight = this.getX1() > this.getX2() ? this.getX1() : this.getX2();

        if (other instanceof Bar) {
            Bar bar = (Bar) other;

            //sorting
            int barTop = bar.getY1() > bar.getY2() ? bar.getY1() : bar.getY2();
            int barBottom = bar.getY1() < bar.getY2() ? bar.getY1() : bar.getY2();
            int barLeft = bar.getX1() < bar.getX2() ? bar.getX1() : bar.getX2();
            int barRight = bar.getX1() > bar.getX2() ? bar.getX1() : bar.getX2();


            return !(thisTop < barBottom || thisBottom > barTop
                    || thisLeft > barRight || thisRight < barLeft);
        }

        if (other instanceof Point) {
            Point point = (Point) other;

            return point.getX() <= thisRight && point.getX() >= thisLeft
                    && point.getY() <= thisTop && point.getY() >= thisBottom;
        }

        return true;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        if (((getX1() == bar.getX1() && getX2() == bar.getX2())
                || (getX2() == bar.getX1() && getX1() == bar.getX2()))
                && ((getY1() == bar.getY1() && getY2() == bar.getY2())
                || (getY2() == bar.getY1() && getY1() == bar.getY2())))
            return true;
        else return false;

    }
}
