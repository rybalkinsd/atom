package ru.atom.model.data;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public class Match {
    private int a, b;

    public Match(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "Match{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
