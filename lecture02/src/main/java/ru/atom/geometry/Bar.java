package ru.atom.geometry;

         public class Bar implements Collider {

             private final int xLeft;
             private final int xRight;
             private final int yTop;
             private final int yBot;

             public Bar(int x1, int y1, int x2, int y2) {
                if (x1 >= x2) {
                        xLeft = x2;
                        xRight = x1;
                    } else {
                        xLeft = x1;
                        xRight = x2;
                    }
                if (y1 >= y2) {
                        yTop = y1;
                        yBot = y2;
                    } else {
                        yTop = y2;
                        yBot = y1;
                    }
            }

             public int getxLeft() {
                return xLeft;
            }

             public int getxRight() {
                return xRight;
           }

             public int getyTop() {
                return yTop;
            }

             public int getyBot() {
                return yBot;
            }

            @Override
            public boolean equals(Object o) {

                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Bar bar = (Bar) o;

            if (this.xLeft == bar.getxLeft() && this.yTop == bar.getyTop()
                     && this.xRight == bar.getxRight() && this.yBot == bar.getyBot()) {
                return true;
              }

              return false;
           }

     @Override
     public boolean isColliding(Collider other) {
         if (other.getClass() == this.getClass()) {
             Bar bar = (Bar) other;
             Point point1 = new Point(xLeft, yTop);
             Point point2 = new Point(xLeft, yBot);
             Point point3 = new Point(xRight, yTop);
             Point point4 = new Point(xRight, yBot);
             if (bar.isColliding(point1) || bar.isColliding(point2)
                     || bar.isColliding(point3) || bar.isColliding(point4)) {
                 return true;
             } else {
                 point1 = new Point(bar.getxLeft(), bar.getyTop());
                 point2 = new Point(bar.getxLeft(), bar.getyBot());
                 point3 = new Point(bar.getxRight(), bar.getyTop());
                 point4 = new Point(bar.getxRight(), bar.getyBot());
                 if (this.isColliding(point1) || this.isColliding(point2)
                         || this.isColliding(point3) || this.isColliding(point4)) {
                     return true;
                 }
             }
             return false;
         } else {
             Point point = (Point) other;
             return this.xLeft <= point.getX() && this.xRight >= point.getX()
                     && this.yTop >= point.getY() && this.yBot <= point.getY();
        }
    }
 }