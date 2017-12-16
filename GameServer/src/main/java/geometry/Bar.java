package geometry;

import objects.Bomb;
import objects.Player;
import objects.Fire;
import objects.GameObject;
import objects.Wood;
import objects.Tickable;
import objects.Movable;
import objects.Wall;
import objects.Positionable;

import java.util.Objects;

public class Bar implements Collider {
    private Point firstPoint;
    private Point secondPoint;

    public Bar(Point p1, Point p2) {
        this.firstPoint = p1;
        this.secondPoint = p2;
    }

    public Bar(Point p1) {
        this.firstPoint = p1;
        this.secondPoint = new Point(p1.getX() + GameObject.width, p1.getY()  + GameObject.height);
    }

    public Bar(Player player) {
        this.firstPoint = new Point(player.getPosition().getX() + 5, player.getPosition().getY() + 5);
        this.secondPoint = new Point(player.getPosition().getX() + GameObject.width - 5,
                player.getPosition().getY() + GameObject.height - 5);
    }

    public Bar(Bomb bomb) {
        this.firstPoint = new Point(bomb.getPosition().getX(), bomb.getPosition().getY());
        this.secondPoint = new Point(bomb.getPosition().getX() + GameObject.width,
                bomb.getPosition().getY() + GameObject.height);
    }

    public Bar(Wall wall) {
        this.firstPoint = new Point(wall.getPosition().getX(), wall.getPosition().getY());
        this.secondPoint = new Point(wall.getPosition().getX() + GameObject.width,
                wall.getPosition().getY() + GameObject.height);
    }

    public Bar(Wood wood) {
        this.firstPoint = new Point(wood.getPosition().getX(), wood.getPosition().getY());
        this.secondPoint = new Point(wood.getPosition().getX() + GameObject.width,
                wood.getPosition().getY() + GameObject.height);
    }


    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if (this.firstPoint.equals(bar.firstPoint) && this.secondPoint.equals(bar.secondPoint)) return true;

        if (this.firstPoint.getX() == bar.secondPoint.getX() && this.firstPoint.getY() == bar.firstPoint.getY()
                && this.secondPoint.getX() == bar.firstPoint.getX()
                && this.secondPoint.getY() == bar.secondPoint.getY()) return true;

        if (this.firstPoint.getX() == bar.firstPoint.getX() && this.secondPoint.getY() == bar.firstPoint.getY()
                && this.secondPoint.getX() == bar.secondPoint.getX()
                && this.firstPoint.getY() == bar.secondPoint.getY()) return true;

        if (this.firstPoint.getX() == bar.secondPoint.getX() && this.secondPoint.getY() == bar.firstPoint.getY()
                && this.secondPoint.getX() == bar.firstPoint.getX()
                && this.firstPoint.getY() == bar.secondPoint.getY()) return true;

        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (this.firstPoint.getX() >= bar.secondPoint.getX() || this.secondPoint.getX() <= bar.firstPoint.getX()
                    || this.firstPoint.getY() >= bar.secondPoint.getY() || this.secondPoint.getY()
                    <= bar.firstPoint.getY()) {
                return false;
            }
            return true;
        }
        if (other instanceof Point) {
            Point point = (Point) other;
            if (this.firstPoint.getX() >= point.getX() || this.secondPoint.getX() <= point.getX()
                    || this.firstPoint.getX() >= point.getY() || this.secondPoint.getY() <= point.getY()) {
                return false;
            }
            return true;
        }
        return false;
    }

}
