package ru.bulls;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Scanner;

public class Game {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Game.class);

    public static void main(String[] args) throws InterruptedException {
        BullsAndCows newSession = new BullsAndCows();
        Scanner scanner = new Scanner(System.in);
        int lives;
        int lenth;
        boolean gameOver = false;
        try {
            newSession.setWords("dictionary.txt");
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return;
        } catch (NullPointerException e) {
            logger.error("can`t load dict");
            return;
        }
        System.out.printf("Welcome to Bulls and Cows game!\n");
        while (!gameOver) {
            lives = 10;
            lenth = newSession.nextWord().length();
            System.out.printf("I offered a %d-letter word, your guess?", lenth);
            while (lives >= 0) {
                String slovoPlayer = scanner.nextLine();
                try {
                    BullsAndCows.Result result = newSession.game(slovoPlayer);
                    if (result.getBulls() == lenth) {
                        System.out.printf("You won!\n");
                        break;
                    } else {
                        System.out.printf("you`re wrong,\n bulls : %d\n cows : %d\n lives left : %d\n\n\n",
                                result.getBulls(), result.getCaws(), lives--);
                        //System.out.printf("rigth world is ====%s =====", newSession.getCurrent());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.printf("wronge lenth\n");
                }
            }
            if (lives < 0) System.out.printf("you lose!!\n");
            System.out.printf("One more time? (Y/N)");
            String ans = scanner.nextLine();
            if (ans.equals("Y") || ans.equals("y")) {
                continue;
            } else if (ans.equals("N") || ans.equals("n")) {
                gameOver = true;
            } else {
                System.out.printf("Wrong input.\n");
                gameOver = true;
            }
        }
    }

}
