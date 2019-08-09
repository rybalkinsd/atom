package main.java.hello;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BullsAndCows {
    private int bull;
    private int cow;
    private int chance;

    public void bullsAndCows() throws FileNotFoundException {
        chance = 10;
        ArrayList<Character> chars
                = new ArrayList<Character>(
                Text.cowsAndBulls().chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toList()));

        for (; chance > 0; chance--) {
            bull = 0;
            cow = 0;
            System.out.println("You have " + chance + "chance:");
            ArrayList<Character> in = new ArrayList<Character>();

            do {
                System.out.println("Input " + chars.size() + "letter word");
                in = (ArrayList<Character>) Input.in().chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toList());
            }
            while (chars.size() != in.size());

            for (int i = 0; i < chars.size(); i++) {
                if (chars.get(i) == in.get(i))
                    bull++;
            }
            for (char o : chars) {
                if (in.contains(o)) {
                    cow++;
                    in.set(in.indexOf(o), ' ');
                }
            }
            System.out.println("bulls:" + bull + "cows:" + cow);

            if (chance == 1 && bull != chars.size()) {
                System.out.println("Game over");
                System.out.print("Answer:");
                chars.forEach(o->System.out.print(o));
                System.out.println("");
            }

            if (bull == chars.size()) {
                System.out.println("Congratulations!!! You win");
                chance = 0;
            }

        }

    }
}
