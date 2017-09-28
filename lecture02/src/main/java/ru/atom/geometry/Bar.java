package ru.atom.geometry;

import static java.lang.Math.abs;

/**            ______
 *            |      + secondPoint
 *            |      |
 *            |      |
 *            |      |
 * firstPoint +______|
 **
 *    Get coordinates of any opposite points and calculate firstPoint and secondPoint
 */


public class Bar implements Collider {

    public Point firstPoint;
    public Point secondPoint;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {

        if (firstCornerX < secondCornerX && firstCornerY < secondCornerY) {
            firstPoint = new Point(firstCornerX, firstCornerY);
            secondPoint = new Point(secondCornerX, secondCornerY);
        }

        if (firstCornerX < secondCornerX && firstCornerY > secondCornerY) {
            firstPoint = new Point(firstCornerX, secondCornerY);
            secondPoint = new Point(secondCornerX, firstCornerY);
        }

        if (firstCornerX > secondCornerX && firstCornerY > secondCornerY) {
            firstPoint = new Point(secondCornerX, secondCornerY);
            secondPoint = new Point(firstCornerX, firstCornerY);
        }

        if (firstCornerX > secondCornerX && firstCornerY < secondCornerY) {
            firstPoint = new Point(secondCornerX, firstCornerY);
            secondPoint = new Point(firstCornerX, secondCornerY);
        }
    }

    public boolean isColliding(Collider other) {

        if (other instanceof Point) {
            Point otherPoint = (Point) other;
            return (otherPoint.x >= firstPoint.x && otherPoint.x <= secondPoint.x)
                    && (otherPoint.y >= firstPoint.y && otherPoint.y <= secondPoint.y);
        } else {
            Bar otherBar = (Bar) other;

            return (abs(otherBar.firstPoint.x - firstPoint.x) <= abs(secondPoint.x - firstPoint.x))
                    && (abs(secondPoint.y - otherBar.secondPoint.y) <= (abs(secondPoint.y - firstPoint.y)));
        }
    }

    /**
     * @param o - other object to check equality with
     * @return true if two bars are equal and not null. Equal means one size too.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        // compare the area of rectangles
        return abs(secondPoint.x - firstPoint.x) * abs(secondPoint.y - firstPoint.y)
                == abs(bar.secondPoint.x - bar.firstPoint.x) * abs(bar.secondPoint.y - bar.firstPoint.y);
    }
}
