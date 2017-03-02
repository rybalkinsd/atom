package ru.atom.geometry;

/**
 * Created by Юля on 02.03.2017.
 */
public class Bar implements Collider /* super class and interfaces here if necessary */ {
    int firstPointX;
    int firstCornerY;
    int secondCornerX;
    int secondCornerY;// fields

    // and methods
    Bar() {
    }

    Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = firstPointX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        return isColliding(bar);// your code here
        //throw new NotImplementedException();
    }


    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) {
            Point point = (Point) other;
            if ((((firstPointX < secondCornerX)
                    && (point.x <= secondCornerX)
                    && (point.x >= firstPointX))
                    && ((firstCornerY < secondCornerY)
                    && (point.y <= secondCornerY)
                    && (point.y >= firstCornerY)))
                    || (((firstPointX > secondCornerX)
                    && (point.x >= secondCornerX)
                    && (point.x <= firstPointX))
                    &&
                    ((firstCornerY < secondCornerY)
                            && (point.y <= secondCornerY)
                            && (point.y >= firstCornerY)))
                    || (((firstPointX > secondCornerX)
                    && (point.x >= secondCornerX)
                    && (point.x <= firstPointX))
                    && ((firstCornerY < secondCornerY)
                    && (point.y <= secondCornerY)
                    && (point.y >= firstCornerY)))
                    || (((firstPointX < secondCornerX)
                    && (point.x <= secondCornerX)
                    && (point.x >= firstPointX))
                    && ((firstCornerY < secondCornerY)
                    && (point.y >= secondCornerY)
                    && (point.y <= firstCornerY)))) {
                return true;
            }
            return false;
        }
        Bar bar = (Bar) other;
        if ((firstPointX == bar.firstPointX
                && firstCornerY == bar.firstCornerY
                && secondCornerX == bar.secondCornerX
                &&
                secondCornerY == bar.secondCornerY)
                || (firstPointX == bar.firstPointX
                && secondCornerX == bar.secondCornerX
                && firstCornerY == bar.secondCornerY
                && secondCornerY == bar.firstCornerY)
                || (firstPointX == bar.secondCornerX
                && secondCornerX == bar.firstPointX
                && firstCornerY == bar.firstCornerY
                && secondCornerY == bar.secondCornerY)
                || (firstPointX == bar.secondCornerX
                && firstCornerY == bar.secondCornerY
                && secondCornerX == bar.firstPointX
                && secondCornerY == bar.firstCornerY)
                ) {
            return true;
        }

        if (((((firstPointX < secondCornerX)
                && (bar.firstPointX <= secondCornerX)
                && (bar.firstPointX >= firstPointX))
                && ((firstCornerY < secondCornerY)
                && (bar.firstCornerY <= secondCornerY)
                && (bar.firstCornerY >= firstCornerY)))
                || (((firstPointX > secondCornerX)
                && (bar.firstPointX >= secondCornerX)
                && (bar.firstPointX <= firstPointX))
                && ((firstCornerY < secondCornerY)
                && (bar.firstCornerY <= secondCornerY)
                && (bar.firstCornerY >= firstCornerY)))
                || (((firstPointX > secondCornerX)
                && (bar.firstPointX >= secondCornerX)
                && (bar.firstPointX <= firstPointX))
                && ((firstCornerY < secondCornerY)
                && (bar.firstCornerY <= secondCornerY)
                && (bar.firstCornerY >= firstCornerY)))
                || (((firstPointX < secondCornerX)
                && (bar.firstPointX <= secondCornerX)
                && (bar.firstPointX >= firstPointX))
                && ((firstCornerY < secondCornerY)
                && (bar.firstCornerY >= secondCornerY)
                && (bar.firstCornerY <= firstCornerY))))
                || ((((firstPointX < secondCornerX)
                && (bar.secondCornerX <= secondCornerX)
                && (bar.secondCornerX >= firstPointX))
                && ((firstCornerY < secondCornerY)
                && (bar.secondCornerY <= secondCornerY)
                && (bar.secondCornerY >= firstCornerY)))
                || (((firstPointX > secondCornerX)
                && (bar.secondCornerX >= secondCornerX)
                && (bar.secondCornerX <= firstPointX))
                && ((firstCornerY < secondCornerY)
                && (bar.secondCornerY <= secondCornerY)
                && (bar.secondCornerY >= firstCornerY)))
                || (((firstPointX > secondCornerX)
                && (bar.secondCornerX >= secondCornerX)
                && (bar.secondCornerX <= firstPointX))
                && ((firstCornerY < secondCornerY)
                && (bar.secondCornerY <= secondCornerY)
                && (bar.secondCornerY >= firstCornerY)))
                || (((firstPointX < secondCornerX)
                && (bar.secondCornerX <= secondCornerX)
                && (bar.secondCornerX >= firstPointX))
                && ((firstCornerY < secondCornerY)
                && (bar.secondCornerY >= secondCornerY)
                && (bar.secondCornerY <= firstCornerY))))) {
            return true;
        }
        return false;// your code here
        //throw new NotImplementedException();
    }
}

