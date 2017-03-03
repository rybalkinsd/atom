package ru.atom.geometry;

public class Bar implements Collider {
    int firstPointX;
    int firstCornerY;
    int secondCornerX;
    int secondCornerY;

    Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        // ориентирование по диагонали
        if ((firstPointX > secondCornerX) && (firstCornerY > secondCornerY)) {
            this.firstPointX = secondCornerX;
            this.secondCornerX = firstPointX;
            this.firstCornerY = secondCornerY;
            this.secondCornerY = firstCornerY;
        } else if (firstPointX > secondCornerX) {
            this.firstPointX = secondCornerX;
            this.secondCornerX = firstPointX;
            this.firstCornerY = firstCornerY;
            this.secondCornerY = secondCornerY;
        } else if (firstCornerY > secondCornerY) {
            this.firstPointX = firstPointX;
            this.secondCornerX = secondCornerX;
            this.firstCornerY = secondCornerY;
            this.secondCornerY = firstCornerY;
        } else {
            this.firstPointX = firstPointX;
            this.firstCornerY = firstCornerY;
            this.secondCornerX = secondCornerX;
            this.secondCornerY = secondCornerY;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        if (((bar.firstCornerY > this.secondCornerY) || (bar.secondCornerY < this.firstCornerY))
                || ((bar.firstPointX > this.secondCornerX) || (bar.secondCornerX < this.firstPointX))) {
            return false;
        } else {
            return true;
        }
    }

    public boolean pointInsideBarCheck(Object o) {
        Point point = (Point) o;
        if ((point.y <= this.secondCornerY) && (point.y >= this.firstCornerY)
                && (point.x <= this.secondCornerX) && (point.x >= this.firstPointX)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return this.equals(other);
        } else if (other instanceof Point) {
            return this.pointInsideBarCheck(other);
        } else {
            return false;
        }
    }

}
