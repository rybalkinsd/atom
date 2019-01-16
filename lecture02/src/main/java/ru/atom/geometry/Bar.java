package ru.atom.geometry;

public class Bar implements Collider {

    private int firstX = 0;
    private int firstY = 0;
    private int secondX = 0;
    private int secondY = 0;

    public Bar(int firstX, int firstY, int secondX, int secondY) {
        this.firstX = Math.min(firstX, secondX);
        this.firstY = Math.min(firstY, secondY);
        this.secondX = Math.max(firstX, secondX);
        this.secondY = Math.max(firstY, secondY);
    }

    public int getFirstX() {
        return firstX;
    }

    public int getSecondX() {
        return secondX;
    }

    public int getFirstY() {
        return firstY;
    }

    public int getSecondY() {
        return secondY;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Bar bar = (Bar) obj;

        return this.firstX == bar.getFirstX()
                && this.secondX == bar.getSecondX()
                && this.firstY == bar.getFirstY()
                && this.secondY == bar.getSecondY();
    }


    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;

            return point.getX() >= this.firstX
                    && point.getX() <= this.secondX
                    && point.getY() >= this.firstY
                    && point.getY() <= this.secondY;
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;

            if (this.firstX > bar.getSecondX()
                    || this.secondX < bar.getFirstX()
                    || this.firstY > bar.getSecondY()
                    || this.secondY < bar.getFirstY()) return false;

            return true;
        }

        return false;
    }
}
