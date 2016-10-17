package ru.atom.model.data;

public class Match {
    private int a, b;

    public Match(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public Match reverse() {
        return new Match(b, a);
    }

    @Override
    public String toString() {
        return "Match{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

}
