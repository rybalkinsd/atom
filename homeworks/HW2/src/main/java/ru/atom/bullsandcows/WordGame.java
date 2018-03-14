package ru.atom.bullsandcows;

import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WordGame {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(WordGame.class);

    public static void main(String[] args) {
        System.out.println("Welcome to the Bulls'n'Cows word game!");
        WordGame game = new WordGame();
        game.startGame(10);
        System.out.println("See you next time!");
    }

    public void startGame(int maxTries) {
        WordDatabase db;
        try {
            db = new WordDatabase("dictionary.txt");
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int triesLeft;
        String word;
        String answer;
        boolean victory;
        do {
            victory = false;
            triesLeft = maxTries;
            word = db.getRandomWord();
            System.out.printf("The word has %d letters.\n" +
                    "Guess it from %d attempts!\n", word.length(), maxTries);
            do {
                triesLeft--;
                System.out.print(">");
                answer = scanner.nextLine().trim();
                while (!(answer.matches("^[A-Za-z]*$") && answer.length() == word.length())) {
                    if (!(answer.length() == word.length()))
                        System.out.println("Mind the word length!");
                    else
                        System.out.println("Only english characters allowed!");
                    System.out.print(">");
                    answer = scanner.nextLine().trim();
                }
                ResultOfChecking result = checkAnswer(word, answer);
                if (result.getBulls() == word.length()) {
                    victory = true;
                    System.out.printf("Congratulations, that's right!\n" +
                            "You guessed it in %d tries!\n", maxTries - triesLeft);
                    break;
                } else
                    System.out.println("You've got " + result);
            } while (triesLeft > 0);
            if (!victory)
                System.out.printf("Game over :(\nThe word was %s.\n", word);
            System.out.println("Want to play again? (y/n)");
        } while (scanner.nextLine().toLowerCase().matches("^yes|y$"));
    }

    public static ResultOfChecking checkAnswer(String correct, String answer) {
        int bulls = 0;
        int cows = 0;
        List<Character> correctChars = correct.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<Character> answerChars = answer.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        for (int i = 0; i < correct.length(); i++) {
            if (correct.charAt(i) == answer.charAt(i)) {
                correctChars.remove(i - bulls);
                answerChars.remove(i - bulls);
                bulls++;
            }
        }
        int idx;
        for (char c : answerChars) {
            idx = correctChars.indexOf(c);
            if (idx >= 0) {
                correctChars.remove(idx);
                cows++;
            }
        }
        return new ResultOfChecking(bulls, cows);
    }

    static class ResultOfChecking {
        private final int bulls;
        private final int cows;

        public ResultOfChecking(int bulls, int cows) {
            this.bulls = bulls;
            this.cows = cows;
        }

        public int getBulls() {
            return bulls;
        }

        public int getCows() {
            return cows;
        }

        @Override
        public String toString() {
            return String.format("%d bull(s) and %d cow(s)", bulls, cows);
        }
    }
}

