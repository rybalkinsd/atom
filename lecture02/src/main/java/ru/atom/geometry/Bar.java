package ru.atom.geometry;

import java.util.Arrays;

/**
 * Created by User on 28.02.2018.
 * sphere
 */
public class Bar implements Collider {
    private final int firstCornerX;
    private final int firstCornerY;
    private final int secondCornerX;
    private final int secondCornerY;

    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = firstCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    public int[] getCornersInOrder() {
        int[] res = {firstCornerX,firstCornerY,secondCornerX,secondCornerY};
        if (firstCornerX > secondCornerX) {
            res[0] = secondCornerX;
            res[2] = firstCornerX;
        }
        if (firstCornerY > secondCornerY) {
            res[1] = secondCornerY;
            res[3] = firstCornerY;
        }
        return res;
    }

    public boolean isPointInsideBar(int pointX, int pointY) {
        int[] barXy = getCornersInOrder();
        return (pointX >= barXy[0]
                && pointX <= barXy[2]
                && pointY >= barXy[1]
                && pointY <= barXy[3]);
    }


    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) {
            return true;
        }
        if (other.getClass() == Point.class) {
            Point point = (Point) other;
            return isPointInsideBar(point.getXy()[0],point.getXy()[1]);
        }
        if (other.getClass() == Bar.class) {
            Bar bar = (Bar) other;
            int[] barXy = bar.getCornersInOrder();
            return (isPointInsideBar(barXy[0],barXy[1])
                    || isPointInsideBar(barXy[0],barXy[3])
                    || isPointInsideBar(barXy[2],barXy[1])
                    || isPointInsideBar(barXy[2],barXy[3]));

        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Bar.class) {
            return false;
        }
        Bar bar = (Bar) obj;
        return Arrays.equals(bar.getCornersInOrder(),this.getCornersInOrder());
    }
}
