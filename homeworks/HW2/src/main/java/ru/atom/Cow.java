package main.java.ru.atom;

import java.util.ArrayList;

public class Cow {
    private int cow=0;
    public void count(ArrayList in, ArrayList <Character> word) {
        for (char o : word) {
            if (in.contains(o)) {
                cow++;
                in.set(in.indexOf(o), ' ');
            }
        }
    }

    public int getCow() {
        return cow;
    }
}