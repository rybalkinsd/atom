package ru.atom.util;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sergey on 2/2/17.
 */
public class V {
    private static final double TOLERANCE = 0.01;

    private final double x, y;

    private V(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static V of(double x, double y) {
        return new V(x, y);
    }

    public V move(double dx, double dy) {
        return new V(x + dx, y + dy);
    }
    public V move(V v) {
        return new V(x + v.x, y + v.y);
    }

    public V times(long v) {
        return new V(x * v, y * v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        V v = (V) o;

        return Math.abs(x - v.x) < TOLERANCE
                && Math.abs(y - v.y) < TOLERANCE;
    }

    @Override
    public int hashCode() {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
