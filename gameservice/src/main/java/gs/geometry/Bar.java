package gs.geometry;

import gs.model.Bomb;
import gs.model.GameObject;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Bar implements Collider {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Bomb.class);

    private final Point leftPoint; //lower left corner
    private final Point rightPoint; //upper right corner


    public Bar(int x1, int y1, int x2, int y2) {
        leftPoint = new Point(Math.min(x1, x2), Math.min(y1, y2));
        rightPoint = new Point(Math.max(x1, x2), Math.max(y1, y2));
    }

    public Bar(Point point) {
        this.leftPoint = point;
        this.rightPoint = new Point(point.getX() + GameObject.getWidthBox(), point.getY() + GameObject.getHeightBox());
    }

    public static ArrayList<ArrayList<Bar>> getExplosions(ArrayList<ArrayList<Point>> points) {
        ArrayList<ArrayList<Bar>> allExplosions = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            ArrayList<Bar> explosion = new ArrayList<>();
            for (int j = 0; j < points.get(i).size(); j++) {
                Bar bar = new Bar(points.get(i).get(j));
                explosion.add(bar);
            }
            allExplosions.add(explosion);
        }

        return allExplosions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if (leftPoint.equals(bar.leftPoint)
                && rightPoint.equals(bar.rightPoint)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == this.getClass()) {
            Bar bar = (Bar) other;
            return leftPoint.getX() >= bar.rightPoint.getX()
                    || rightPoint.getX() <= bar.leftPoint.getX()
                    || leftPoint.getY() >= bar.rightPoint.getY()
                    || rightPoint.getY() <= bar.leftPoint.getY();
        } else if (other.getClass() == Point.class) {
            Point point = (Point) other;
            return leftPoint.getX() >= point.getX() || leftPoint.getY() >= point.getY()
                    || rightPoint.getX() <= point.getX() || rightPoint.getY() <= point.getY();
        } else throw new IllegalArgumentException();
    }

    public Point getLeftPoint() {
        return leftPoint;
    }
}