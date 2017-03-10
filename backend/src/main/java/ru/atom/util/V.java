package ru.atom.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sergey on 2/2/17.
 */
public class V {
    private static final double TOLERANCE = 0.01;
    public static final V ZERO = V.of(0, 0);

    @JsonProperty
    private final double x, y;

    private V(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @JsonCreator
    public static V of(@JsonProperty("x") double x,  @JsonProperty("y") double y) {
        return new V(x, y);
    }

    public V move(double dx, double dy) {
        return V.of(x + dx, y + dy);
    }
    public V move(V v) {
        return V.of(x + v.x, y + v.y);
    }

    public V times(long m) {
        return V.of(x * m, y * m);
    }

    public V times(double mx, double my) {
        return V.of(x * mx, y * my);
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
