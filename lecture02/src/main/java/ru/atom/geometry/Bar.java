package ru.atom.geometry;

import java.util.Objects;

public class Bar implements Collider {
    private Point firstCorner;
    private Point secondCorner;

    public Bar(Point firstCorner, Point secondCorner) {
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
    }

    public Point getFirstCorner() {
        return firstCorner;
    }

    public Point getSecondCorner() {
        return secondCorner;
    }

    public Point getThirdCorner() {
        return new Point(firstCorner.getX(), secondCorner.getY());
    }

    public Point getFourthCorner() {
        return new Point(secondCorner.getX(), firstCorner.getY());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;

        if (this.firstCorner.equals(bar.firstCorner)
                && this.secondCorner.equals(bar.secondCorner)) {
            return true;
        } else if (this.firstCorner.equals(bar.secondCorner)
                && this.secondCorner.equals(bar.firstCorner)) {
            return true;
        } else if (this.getFirstCorner().getX() == bar.getFirstCorner().getX() && this.getFirstCorner().getY() == bar.getSecondCorner().getY()
                && this.getSecondCorner().getX() == bar.getSecondCorner().getX() && this.getSecondCorner().getY() == bar.getFirstCorner().getY()) {
            return true;
        } else if (this.getFirstCorner().getX() == bar.getSecondCorner().getX() && this.getFirstCorner().getY() == bar.getFirstCorner().getY()
                && this.getSecondCorner().getX() == bar.getFirstCorner().getX() && this.getSecondCorner().getY() == bar.getSecondCorner().getY()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstCorner, secondCorner);
    }

    private boolean between(int value, int first, int second) {
        return (value >= Math.min(first, second) && value <= Math.max(first, second));
    }

    private boolean between(Point value, Point first, Point second) {
        return between(value.getX(), first.getX(), second.getX())
                && between(value.getY(), first.getY(), second.getY());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (this.equals(bar)) {
                return true;
            } else {
                return this.isColliding(bar.getFirstCorner()) || this.isColliding(bar.getSecondCorner())
                        || this.isColliding(bar.getThirdCorner()) || this.isColliding(bar.getFourthCorner())
                        ||  (between(this.getFirstCorner().getX(), bar.getFirstCorner().getX(), bar.getSecondCorner().getX())
                            || between(this.getSecondCorner().getX(), bar.getFirstCorner().getX(), bar.getSecondCorner().getX())
                            ||  between(bar.getFirstCorner().getX(), this.getFirstCorner().getX(), this.getSecondCorner().getX())
                            ||  between(bar.getSecondCorner().getX(), this.getFirstCorner().getX(), this.getSecondCorner().getX()))
                        && (between(this.getFirstCorner().getY(), bar.getFirstCorner().getY(), bar.getSecondCorner().getY())
                            || between(this.getSecondCorner().getY(), bar.getFirstCorner().getY(), bar.getSecondCorner().getY())
                            || between(bar.getFirstCorner().getY(), this.getFirstCorner().getY(), this.getSecondCorner().getY())
                            || between(bar.getSecondCorner().getY(), this.getFirstCorner().getY(), this.getSecondCorner().getY()));
            }
        } else if (other instanceof Point) {
            Point point = (Point) other;
            return between(point, this.getFirstCorner(), this.getSecondCorner());
        } else
            return false;
    }
}
