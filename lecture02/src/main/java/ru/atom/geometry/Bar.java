package ru.atom.geometry;

public class Bar implements Collider {
    private int firstX;
    private int firstY;
    private int secondX;
    private int secondY;

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other))
            return true;
        if (other instanceof Bar && this instanceof Bar) {
            Bar bar = (Bar) other;
            if (this.firstX >= bar.firstX && this.secondX <= bar.secondX ||
                    this.firstX >= bar.secondX && this.secondX <= bar.firstX ||
                    this.firstX <= bar.firstX && this.secondX >= bar.secondX ||
                    this.firstX <= bar.secondX && this.secondX >= bar.firstX) {
                if (this.firstY >= bar.firstY && this.secondY <= bar.secondY ||
                        this.firstY >= bar.secondY && this.secondY <= bar.firstY ||
                        this.firstY <= bar.firstY && this.secondY >= bar.secondY ||
                        this.firstY <= bar.secondY && this.secondY >= bar.firstY)
                    return true;
                else return false;
            } else return false;
        } else {
            if (this instanceof Bar) {
                Point point = (Point) other;
                if (this.firstX <= point.getX() && this.secondX >= point.getX() ||
                        this.firstX >= point.getX() && this.secondX <= point.getX()) {
                    if (this.firstY <= point.getY() && this.secondY >= point.getY() ||
                            this.firstY >= point.getY() && this.secondY <= point.getY())
                        return true;
                    else return false;
                } else return false;
            } else return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

            Bar bar = (Bar) o;

            if (this.firstX == bar.firstX && this.secondX == bar.secondX || this.firstX == bar.secondX && this.secondX == bar.firstX) {
                if (this.firstY == bar.firstY && this.secondY == bar.secondY || this.firstY == bar.secondY && this.secondY == bar.firstY) {
                    return true;
                } else return false;
            } else return false;

    }

    public void setFirstX(int firstX) {
        this.firstX = firstX;
    }

    public void setFirstY(int firstY) {
        this.firstY = firstY;
    }

    public void setSecondX(int secondX) {
        this.secondX = secondX;
    }

    public void setSecondY(int secondY) {
        this.secondY = secondY;
    }
}
