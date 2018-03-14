package ru.atom;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class BaCMenu {


    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BullsAndCows.class);



    public static void main(String[] args) {
        String dictFilename = "./src/main/resources/dictionary.txt";

        BullsAndCows game = null;
        try  {
            game = new BullsAndCows(dictFilename);
        } catch (FileNotFoundException fnfe) {
            log.error("Can't open file {}" , dictFilename);
        } catch (IOException ioe) {
            log.error("Can't read file {} ", dictFilename);
        }

        game.startGame();
        game.printWelcome();
        Scanner sc = new Scanner(System.in);


        while (1 > 0) {
            //System.out.println("Cheats activated :" + game.getCurrentWord());


            while (!game.isGameFinished()) {
                String newStr = sc.nextLine().trim();
                if (game.checkInputAnswer(newStr)) {
                    int[] bullscows = game.makeTurn(newStr);
                    if (bullscows[0] == game.getCurrentWord().length()) {
                        game.finishGame();
                        game.printCongratulations();
                    } else {
                        System.out.println("Bulls: " + bullscows[0] + "\nCows: " + bullscows[1]);
                        if (game.isGameFinished())
                            game.printLooseText();
                    }

                } else {
                    System.out.println("Wrong input answer try again. Remember all letters are latin. ");
                    System.out.println("P.S. and don't forget about word length =)");
                }
            }

            System.out.println("Wanna try again? Y/N");
            String usrDecision = sc.nextLine().trim();
            if (!(usrDecision.toUpperCase().charAt(0) == 'Y')) {
                break;
            } else
                game.restartGame();
        }

        // this is a place where carpet ends
        return;


    }
}
