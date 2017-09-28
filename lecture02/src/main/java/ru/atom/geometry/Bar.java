package ru.atom.geometry;

/**
 * Created by Станислав on 27.09.2017.
 */
public class Bar implements Collider /* super class and interfaces here if necessary */ {

    private int firsCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public int getfirsCornerX() {
        return firsCornerX;
    }

    public int getfirstCornerY() {
        return firstCornerY;
    }

    public int getsecondCornerX() {
        return secondCornerX;
    }

    public int getsecondCornerY() {
        return secondCornerY;
    }

    Bar(int firsCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firsCornerX = firsCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        int ymax;
        int xmax;
        int ymin;
        int xmin;

        if (this.firsCornerX > this.secondCornerX) {
            xmax = this.firsCornerX;
            xmin = this.secondCornerX;
        } else {
            xmax = this.secondCornerX;
            xmin = this.firsCornerX;
        }
        if (this.firstCornerY > this.secondCornerY) {
            ymax = this.firstCornerY;
            ymin = this.secondCornerY;
        } else {
            ymax = this.secondCornerY;
            ymin = this.firstCornerY;
        }
        Bar bar = (Bar) o;
        int yotherMax;
        int xotherMax;
        int yotherMin;
        int xotherMin;
        if (bar.getfirsCornerX() > bar.getsecondCornerX()) {
            xotherMax = bar.getfirsCornerX();
            xotherMin = bar.getsecondCornerX();
        } else {
            xotherMax = bar.getsecondCornerX();
            xotherMin = bar.getfirsCornerX();
        }
        if (bar.getfirstCornerY() > bar.getsecondCornerY()) {
            yotherMax = bar.getfirstCornerY();
            yotherMin = bar.getsecondCornerY();
        } else {
            yotherMax = bar.getsecondCornerY();
            yotherMin = bar.getfirstCornerY();
        }


        // your code here
        if (xmax == xotherMax && ymax == yotherMax && xmin == xotherMin && ymin == yotherMin) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        int ymax;
        int xmax;
        int ymin;
        int xmin;
        if (this.firsCornerX > this.secondCornerX) {
            xmax = this.firsCornerX;
            xmin = this.secondCornerX;
        } else {
            xmax = this.secondCornerX;
            xmin = this.firsCornerX;
        }
        if (this.firstCornerY > this.secondCornerY) {
            ymax = this.firstCornerY;
            ymin = this.secondCornerY;
        } else {
            ymax = this.secondCornerY;
            ymin = this.firstCornerY;
        }

        // cast from Object to Point
        if (other instanceof Bar) {

            Bar bar = (Bar) other;
            int yotherMax;
            int xotherMax;
            int yotherMin;
            int xotherMin;
            if (bar.getfirsCornerX() > bar.getsecondCornerX()) {
                xotherMax = bar.getfirsCornerX();
                xotherMin = bar.getsecondCornerX();
            } else {
                xotherMax = bar.getsecondCornerX();
                xotherMin = bar.getfirsCornerX();
            }
            if (bar.getfirstCornerY() > bar.getsecondCornerY()) {
                yotherMax = bar.getfirstCornerY();
                yotherMin = bar.getsecondCornerY();
            } else {
                yotherMax = bar.getsecondCornerY();
                yotherMin = bar.getfirstCornerY();
            }

            // your code here
            if ((xmax >= xotherMax && ymax >= yotherMax && xmin <= xotherMax
                    && ymin <= yotherMax) || (xmax >= xotherMin && ymax >= yotherMin
                    && xmin <= xotherMin && ymin <= yotherMin)) {
                return true;
            } else {
                return false;
            }
        }
        if (other instanceof Point) {

            Point point = (Point) other;
            if ((xmin <= point.getX() && ymin <= point.getY()) && (xmax >= point.getX() && ymax >= point.getY())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}


