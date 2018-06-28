package ru.atom;

public class GameJudge {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GameJudge.class);

    public boolean check(String guessWord, String word) {

        char[] guess = guessWord.toCharArray();
        char[] secret = word.toCharArray();
        int bulls = 0;
        int cows = 0;

        if (word.equals(guessWord))
            return true;
        if (word.length() != guessWord.length()) {
            log.warn("Length the guess word must be " + word.length() + "-length");
            return false;
        }
        for (int i = 0; i < secret.length; i++) {
            for (int j = 0; j < guess.length; j++) {
                if (secret[i] == guess[j]) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }

        log.info("Bulls: " + bulls);
        log.info("Cows: " + cows);

        if (bulls == secret.length) {
            return true;
        }
        return false;
    }
}
