package ru.atom;

import java.util.Scanner;

import static ru.atom.GameLogic.checkWords;

public class Game {
    private static boolean Winner;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Game.class);

    public static void main(String[] args) {

        Words game = new Words();
        if (game.dictionary.isEmpty()) {
            log.info("Dictionary is empty, file not load.");
            return;
        }

        System.out.println("Welcome! Try to find secret word. You have only 10 trys.");
        while (true) {
            int rndVal = (int) (Math.random() * 1000000) % game.dictionary.size();
            System.out.println("This " + game.dictionary.get(rndVal).length() + "-letter word");

            Scanner scanner = new Scanner(System.in);

            for (int turn = 1; turn <= 10; turn++) {
                System.out.println("Try #" + turn);
                String userInput = scanner.nextLine();
                Winner = checkWords(userInput, game.dictionary.get(rndVal));
                if (Winner) {
                    System.out.println("U WON!!!\nStart new game? Y/N");
                    break;
                }
            }

            if (!Winner)
                System.out.println("Game over! Secret word : "
                        + game.dictionary.get(rndVal) + "\nStart new game? Y/N ");

            while (true) {
                String checkContiniue = scanner.nextLine();
                if (checkContiniue.equals("N") || checkContiniue.equals("n"))
                    return;
                else if (checkContiniue.equals("Y") || checkContiniue.equals("y")) {
                    System.out.println("Go to next word!");
                    break;
                } else
                    System.out.println("Enter Y or N!");
            }
        }
    }
}
