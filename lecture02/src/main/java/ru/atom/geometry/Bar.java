package ru.atom.geometry;

/**
 * Created by anatoly on 03.03.17.
 */
public class Bar implements Collider {

    private int firstPointX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = Math.min(firstPointX, secondCornerX);
        this.firstCornerY = Math.min(firstCornerY, secondCornerY);
        this.secondCornerX = Math.max(secondCornerX, firstPointX);
        this.secondCornerY = Math.max(secondCornerY, firstCornerY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;


        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        if ((Math.min(bar.firstPointX, bar.secondCornerX) == this.firstPointX)
                && (Math.max(bar.firstPointX, bar.secondCornerX) == this.secondCornerX)
                && (Math.min(bar.firstCornerY, bar.secondCornerY) == this.firstCornerY)
                && (Math.max(bar.firstCornerY, bar.secondCornerY) == this.secondCornerY)) {
            return true;
        } else return false;


    }


    @Override
    public boolean isColliding(Collider other) {

        if (this == other) return true;
        if (other == null)  return false;


        // cast from Object to Point

        if (other instanceof Point) {

            Point point = (Point) other;

            return ((point.getX() <= this.secondCornerX)
                    && (point.getX() >= this.firstPointX)
                    && (point.getY() <= this.secondCornerY)
                    && (point.getY() >= this.firstCornerY));

        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;


            // your code here
            return ((bar.secondCornerX >= firstPointX
                    && bar.secondCornerX <= secondCornerX
                    && bar.secondCornerY <= secondCornerY
                    && bar.secondCornerY >= firstCornerY)
                    || (bar.firstPointX >= firstPointX
                    && bar.firstPointX <= secondCornerX
                    && bar.secondCornerY <= secondCornerY
                    && bar.secondCornerY >= firstCornerY)
                    || (bar.firstPointX >= firstPointX
                    && bar.firstPointX <= secondCornerX
                    && bar.firstCornerY <= secondCornerY
                    && bar.firstCornerY >= firstCornerY)
                    || (bar.secondCornerX >= firstPointX
                    && bar.secondCornerX <= secondCornerX
                    && bar.firstCornerY <= secondCornerY
                    && bar.firstCornerY >= firstCornerY)

                    || (secondCornerX >= bar.firstPointX
                    && secondCornerX <= bar.secondCornerX
                    && firstCornerY <= bar.secondCornerY
                    && firstCornerY >= bar.firstCornerY)
                    || (firstPointX >= bar.firstPointX
                    && firstPointX <= bar.secondCornerX
                    && secondCornerY <= bar.secondCornerY
                    && secondCornerY >= bar.firstCornerY)
                    || (secondCornerX >= bar.firstPointX
                    && secondCornerX <= bar.secondCornerX
                    && secondCornerY <= bar.secondCornerY
                    && secondCornerY >= bar.firstCornerY)
                    || (firstPointX >= bar.firstPointX
                    && firstPointX <= bar.secondCornerX
                    && firstCornerY <= bar.secondCornerY
                    && firstCornerY >= bar.firstCornerY));
        } else return  false;
    }



}

            
        
            

