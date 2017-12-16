package gameobjects;

import geometry.Bar;

import java.util.ArrayList;

public class Cell extends Bar {
    private int x;
    private int y;
    private ArrayList<State> states = new ArrayList<>();

    public Cell(int x, int y, State state) {
        super(x, y, x + 32, y + 32);
        this.states.add(state);
    }

    public void addState(State state) {
        this.states.add(state);
    }

    ;

    public int getX() {
        return this.x * 32;
    }

    public int getY() {
        return this.y * 32;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cell cell = (Cell) o;
        if (this.x == cell.getX() && this.y == cell.getY()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<State> getState() {
        return states;
    }
}
