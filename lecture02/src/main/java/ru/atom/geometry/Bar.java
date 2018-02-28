package ru.atom.geometry;

public class Bar implements Collider{
    private Point left, right;

    public Bar(){
        this(0,0,0,0);
    }

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY){

        setLeft(new Point(Math.min(firstCornerX,secondCornerX),Math.min(firstCornerY,secondCornerY)));
        setRight(new Point(Math.max(firstCornerX, secondCornerX),Math.max(firstCornerY,secondCornerY)));
    }

    public Point getLeft() {
        return left;
    }

    public Point getRight() {
        return right;
    }

    public void setLeft(Point left) {
        this.left = left;
    }

    public void setRight(Point right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        return getLeft().getX() == bar.getLeft().getX() && getLeft().getY() == bar.getLeft().getY() &&
                getRight().getX()== bar.getRight().getX() && getRight().getY() == bar.getRight().getY();
    }

    private boolean isIn(Point point) {
        return (point.getX() <= getRight().getX()
                && point.getY() <= getRight().getY()
                && point.getX() >= getLeft().getX()
                && point.getY() >= getLeft().getY());
    }

    @Override
    public boolean isColliding(Collider other) {
        if(other.getClass() == Bar.class) {
            Bar bar = (Bar) other;
            if (bar.isIn(getLeft()) || bar.isIn(getRight()) || isIn(bar.getLeft()) || isIn(bar.getRight()))
                return true;
            else
                return false;
        }
        if(other.getClass() == Point.class) {
            return isIn((Point)other);
        }
        return false;
    }
}
