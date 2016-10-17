package ru.atom.model.data;


public class Like {
    private int source, target;

    public Like(int source, int target) {
        this.source = source;
        this.target = target;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Like{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}