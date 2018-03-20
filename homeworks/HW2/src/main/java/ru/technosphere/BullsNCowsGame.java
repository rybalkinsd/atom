package ru.technosphere;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;


public class BullsNCowsGame {
    private static final Logger logger = LoggerFactory.getLogger(BullsNCowsGame.class);
    private static final Scanner reader = new Scanner(System.in);
    private static final String dictName = "dictionary.txt";
    private static final Random random = new Random(47);

    public class BullsNCowsResult {

        private final int bulls;
        private final int cows;

        BullsNCowsResult(int bulls, int cows) {
            this.bulls = bulls;
            this.cows = cows;
        }

        public int getBull() {
            return bulls;
        }

        public int getCows() {
            return cows;
        }

    }

    private static List<String> readFromResource(String resourceName)
            throws IOException {
        InputStream inputStream = BullsNCowsGame.class.getClassLoader().getResourceAsStream(resourceName);
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static void main(String[] args) {
        BullsNCowsGame game = new BullsNCowsGame();
        game.play();
    }

    private void play() {
        List<String> words;
        String guess;
        try {
            words = readFromResource(dictName);
        } catch (IOException e) {
            logger.error("Can't find {} in resources directory", dictName);
            logger.error(e.toString());
            return;
        }

        System.out.println("This is Bulls and Cows game.");
        do {
            int attempts = 10;
            String answer = words.get(random.nextInt(words.size()));
            words.remove(answer);
            System.out.println("My word has " + answer.length() + " chars. Your's guess? " + answer);
            do {
                guess = checkWord(answer);
                BullsNCowsResult result = getBullsNCows(guess, answer);
                if (result.getBull() == answer.length()) {
                    System.out.println("Congratulations. You won!");
                    break;
                }
                System.out.println("Okay. You have " + result.getBull() + " bulls and " + result.getCows() + " cows.");
                attempts--;
            } while (attempts != 0);
            if (attempts == 0) System.out.println("You lose");
            System.out.println("Wanna play another game? (y/n)");
        } while (checkAnotherGame());
        System.out.println("Bye!");
    }

    private boolean checkAnotherGame() {
        String userAnswer;
        while (true) {
            userAnswer = reader.nextLine().toLowerCase();
            if (userAnswer.equals("y"))
                return true;
            if (userAnswer.equals("n")) {
                return false;
            } else {
                System.out.println("You should enter 'y' or 'n'.");
            }
        }
    }

    private String checkWord(String answer) {
        while (true) {
            System.out.print("> ");
            String guess = reader.nextLine().trim();
            if (guess.length() != answer.length()) {
                System.out.println("Your's word has " + guess.length() + " chars, " +
                        "but should be equal to " + answer.length() + " chars.");
                continue;
            }
            if (!guess.chars().allMatch(Character::isLetter)) {
                System.out.println("Your's word should contain only english chars");
                continue;
            }
            return guess;
        }
    }

    public BullsNCowsResult getBullsNCows(String guess, String answer) {
        int cows = 0;
        int bulls = 0;
        List<Character> answerChars = answer.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        List<Character> inputChars = guess.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                inputChars.remove(i - bulls); // Make sure, that index is not out of bound
                answerChars.remove(i - bulls);
                bulls++;
            }
        }

        for (Character c : inputChars) {
            if (answerChars.contains(c)) {
                cows++;
                answerChars.remove(c);
            }
        }
        return new BullsNCowsResult(bulls, cows);
    }
}