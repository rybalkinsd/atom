package ru.atom;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int counts = 10;
        Game game = new Game(counts);
        System.out.println("Hello U want to play a game?");
        System.out.println("Y/N?:");
        String chr;
        try (Scanner scanner = new Scanner(System.in)) {
            chr = scanner.nextLine().toLowerCase();
        }
        if (chr.toCharArray()[0] == 'n') {
            game.setStatus(Status.EXIT);
        }
        while (game.getStatus() != Status.EXIT) {
            game.newWord();
            System.out.println("I have a word it consists of " + game.getSecretLen()
                    + " letters");
            System.out.println("Try to guess it with " + game.getCounts() + " attempts");
            String ans; 
            String sysAns;
            int attempt = game.getCounts();
            for (; attempt > 0; attempt--) {

                System.out.println("U have " + attempt + " more attempts ");
                System.out.println("What do u think about the word?");
                System.out.println(game.getSecret());
                ans = game.getNewAnswer();
                sysAns = game.giveCowsnBulls(ans);
                System.out.println(sysAns);
                if (game.getStatus() == Status.WIN) {
                    break;
                }
            }
            if (attempt == 0) {
                System.out.println("sorry, U loose");
            }

            System.out.println("Do U want to play one more time?");
            System.out.println("Y/N?:");
            try (Scanner scanner = new Scanner(System.in)) {
                chr = scanner.nextLine().toLowerCase();
            }
            if (chr.toCharArray()[0] == 'n') {
                game.setStatus(Status.EXIT);
            }

        }
        System.out.println("Bye Bye!");
    }

}
