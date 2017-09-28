package ru.atom.geometry;

/**
 * Template class for
 */
//public class Point /* super class and interfaces here if necessary */ {


    // fields
    // and methods
    public class Point implements Collider {

        private int x;
        private int y;

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

        /**
         * @param o - other object to check equality with
         * @return true if two points are equal and not null.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
        //public boolean equals(Object o) {

            // cast from Object to Point
            Point point = (Point) o;


            // your code here
            //throw new UnsupportedOperationException();
            if (this.x == point.x && this.y == point.y) {
                     return true;
                }
                return false;
            }

             @Override
             public boolean isColliding(Collider other) {

                return (this.equals(other));

             }

}

