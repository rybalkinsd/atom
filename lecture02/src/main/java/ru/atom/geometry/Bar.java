package ru.atom.geometry;

import java.lang.Math;

public class Bar implements Collider {

    Point bot;

    Point top;

    Bar(int x1, int y1, int x2, int y2) {
        if (Math.sqrt(x1 * x1 + y1 * y1) > Math.sqrt(x2 * x2 + y2 * y2)) {
            this.bot = new Point(x2, y2);
            this.top = new Point(x1, y1);
        } else {
            this.bot = new Point(x1, y1);
            this.top = new Point(x2, y2);
        }
    }

    public boolean isColliding(Collider other) {
        if (other.getClass() == Point.class) {
            Point oth = (Point) other;
            return (oth.x >= bot.x) && (oth.y >= bot.y) &&
                    (oth.x <= top.x) && (oth.y <= top.y);
        } else if (other.getClass() == Bar.class) {
            Bar oth = (Bar) other;
            return !(top.y < oth.bot.y || bot.y > oth.top.y ||
                      top.x < oth.bot.x || bot.x > oth.top.x);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bar oth = (Bar) o;
        return (bot.x == oth.bot.x || bot.x == oth.top.x) &&
                (bot.y == oth.bot.y || bot.y == oth.top.y) &&
                 (top.x == oth.bot.x || top.x == oth.top.x) &&
                  (top.y == oth.bot.y || top.y == oth.top.y);
    }
}
