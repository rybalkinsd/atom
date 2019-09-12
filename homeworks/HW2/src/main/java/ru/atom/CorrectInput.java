package main.java.ru.atom;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CorrectInput {
    public  ArrayList<Character> correctInput(int size) {
        int length=0;
        ArrayList<Character> in;
        do {
            System.out.println("Input " + size + "letter word");
            in=new ArrayList<Character>(Input.in().chars()
                    .mapToObj(e -> (char) e)
                    .collect(Collectors.toList()));
            length = in.size();
        }
        while (size != length );
        return in;
    }
}
