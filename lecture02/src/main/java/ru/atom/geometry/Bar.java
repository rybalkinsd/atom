package ru.atom.geometry;

public class Bar implements Collider {
    Point firstCorner;
    Point secondCorner;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        firstCorner = new Point(firstCornerX, firstCornerY);
        secondCorner = new Point(secondCornerX, secondCornerY);
    }

    private boolean contains(Point point) {
        int distanceToOtherCornerX = secondCorner.x - firstCorner.x;
        int distanceToOtherCornerY = secondCorner.y - firstCorner.y;
        int distanceToPointX = point.x - firstCorner.x;
        int distanceToPointY = point.y - firstCorner.y;

        if (
                (distanceToOtherCornerX >= distanceToPointX)
                && (distanceToOtherCornerY >= distanceToPointY)
                && (distanceToPointX * distanceToOtherCornerX >= 0)
                && (distanceToPointY * distanceToOtherCornerY >= 0)
        ) {
            return true;
        }

        return false;
    }
    
    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            
            return this.contains(point);
        }

        if (other instanceof Bar) {
            Bar bar = (Bar) other;

            if (this.equals(bar)) {
                return true;
            }

            /**
             * Check if one of other Bar's corner there is inside current Bar
             */ 

            if (
                    (this.contains(bar.firstCorner))
                    || (this.contains(bar.secondCorner))
                    || (this.contains(new Point(bar.firstCorner.x, bar.secondCorner.y)))
                    || (this.contains(new Point(bar.secondCorner.x, bar.firstCorner.y)))
            ) {
                return true;
            }

            int distanceToOtherCornerX = secondCorner.x - firstCorner.x;
            int distanceToOtherCornerY = secondCorner.y - firstCorner.y;
            int distanseToOtherBarFirstCornerX = bar.firstCorner.x - firstCorner.x;
            int distanseToOtherBarFirstCornerY = bar.firstCorner.y - firstCorner.y;
            int distanseToOtherBarSecondCornerX = bar.secondCorner.x - firstCorner.x;
            int distanseToOtherBarSecondCornerY = bar.secondCorner.y - firstCorner.y;

            if (
                    (distanseToOtherBarFirstCornerY * distanseToOtherBarSecondCornerY < 0)
                    && ((distanseToOtherBarFirstCornerX < distanceToOtherCornerX)
                    || (distanseToOtherBarSecondCornerX < distanceToOtherCornerX))
            ) {
                return true;
            }

            if (
                    (distanseToOtherBarFirstCornerX * distanseToOtherBarSecondCornerX < 0)
                    && ((distanseToOtherBarFirstCornerY < distanceToOtherCornerY)
                    || (distanseToOtherBarSecondCornerY < distanceToOtherCornerY))
            ) {
                return true;
            }

        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;
        
        if (
                (firstCorner.equals(bar.firstCorner))
                && (secondCorner.equals(bar.secondCorner))
        ) {
            return true;
        }

        if (
                (secondCorner.equals(bar.firstCorner))
                && (firstCorner.equals(bar.secondCorner))
        ) {
            return true;
        }

        if (
                (firstCorner.equals(new Point(bar.firstCorner.x, bar.secondCorner.y)))
                && (secondCorner.equals(new Point(bar.secondCorner.x, bar.firstCorner.y)))
        ) {
            return true;
        }

        if (
                (firstCorner.equals(new Point(bar.secondCorner.x, bar.firstCorner.y)))
                && (secondCorner.equals(new Point(bar.firstCorner.x, bar.secondCorner.y)))
        ) {
            return true;
        }

        return false;
    }
}
