package ru.bullsncows;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Game.class);

    public static void main(String[] args) throws InterruptedException {
        BaC baC = new BaC();
        Scanner scanner = new Scanner(System.in);
        int tries;
        try {
            baC.setWords("dictionary.txt");
        } catch (IOException e) {
            logger.debug(e.getLocalizedMessage());
            return;
        } catch (NullPointerException e) {
            logger.error("Cannot load dictionary");
            return;
        }
        System.out.println("Hi!\nWanna play some Bulls and Cows?");
        int length;
        do {
            tries = 10;
            length = baC.nextWord().length();
            Thread.sleep(1000);
            System.out.printf("I got a word for ya\nIt has %d letters. Go on!\n", length);
            do {
                String guess = scanner.nextLine();
                Thread.sleep(500);
                try {
                    BaC.Overlap overlap = baC.getOverlap(guess);
                    if (overlap.getB() == length) {
                        System.out.println("Grats! You guessed it right!");
                        break;
                    } else
                        System.out.printf("Nah, %d bulls, %d cows\n%d tries left\n",
                                overlap.getB(),
                                overlap.getC(),
                                tries--);

                } catch (IllegalArgumentException e) {
                    System.out.printf("%d letters actshually\n", length);
                }
            } while (tries > 0);
            if (tries == 0) System.out.println("Better luck next time!");
            System.out.println("One more time? (Y)");
        } while (scanner.nextLine().equals("Y"));
    }
}
