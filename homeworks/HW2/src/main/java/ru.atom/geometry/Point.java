package ru.atom.geometry;

import ru.atom.model.Positionable;

//TODO insert your implementation of geometry here
public class Point implements Collider,Positionable {
    private  int x;
    private  int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    Rectangle space = new Rectangle(this.x,this.x,this.y,this.y);

    @Override
    public Point getPosition() {
        return this;
    }

    @Override
    public Rectangle getSpace() {
        return space;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        }
        throw new UnsupportedOperationException();
    }
}
