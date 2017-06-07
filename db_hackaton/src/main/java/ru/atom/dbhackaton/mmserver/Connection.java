package ru.atom.dbhackaton.mmserver;

/**
 * Created by ikozin on 17.04.17.
 */
public class Connection {
    private final String name;

    public Connection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Connection{" +
                ", name='" + name + '\'' +
                '}';
    }
}
