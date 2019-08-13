package main.java.ru.atom;

import java.util.ArrayList;

public class Bull {
    private int bull=0;
    public void count(ArrayList in,ArrayList word) {
        for (int i = 0; i < word.size(); i++) {
            if (word.get(i).equals(in.get(i))) {
                bull++;
            }
        }

        Winner win=new Winner();

    }

    public int getBull() {
        return bull;
    }
}
