package ru.atom.geometry;

public class Rect implements Collider  {
    private final Point v1;
    private final Point v2;

    //реализовываем два конструктора: один для клонирования, другой для создания по двум точкам
    public Rect(Point v1, Point v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Rect(Rect other) {
        this.v1 = other.v1;
        this.v2 = other.v2;
    }

    public Point getV1() {
        return this.v1;
    }

    public Point getV2() {
        return this.v2;
    }

    public boolean isColliding(Collider o) {
        if (!(o instanceof Rect)) {
            return false;
        }
        Rect other = (Rect) o;
        //создаем интервалы на основе данного прямоугольника и на основе другого
        Interval thisXInterval = new Interval(this.getV1().getX(), this.getV2().getX());
        Interval thisYInterval = new Interval(this.getV1().getY(), this.getV2().getY());
        Interval otherXInterval = new Interval(other.getV1().getX(), other.getV2().getX());
        Interval otherYInterval = new Interval(other.getV1().getY(), other.getV2().getY());
        //проверяем пересечение
        if (thisXInterval.isColliding(otherXInterval) && thisYInterval.isColliding(otherYInterval)) {
            return true;
        }
        return false;
    }
}