package ru.atom.game;

import ru.atom.game.exception.LengthMissMatchException;
import ru.atom.game.exception.ResourceReadException;
import ru.atom.game.state.State;
import ru.atom.game.util.BullCounter;
import ru.atom.game.util.CowCounter;
import ru.atom.game.util.WordHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.InputMismatchException;

public class CowsAndBulls {
    private static final int TRY_COUNT = 10;
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CowsAndBulls.class);
    private PrintStream out;
    private BufferedReader reader;

    public CowsAndBulls(PrintStream out, BufferedReader reader) {
        this.out = out;
        this.reader = reader;
        LOGGER.info("Cows And Bull Game created");
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            new CowsAndBulls(System.out, reader).start();
        } catch (ResourceReadException exc) {
            LOGGER.error("Can't read resource file.\n" + exc.getMessage());
            exc.printStackTrace();
        } catch (IOException exc) {
            LOGGER.error("Error when closing reader.\n" + exc.getMessage());
            exc.printStackTrace();
        } catch (IllegalArgumentException | InputMismatchException exc) {
            LOGGER.error("Illegal argument error.\n" + exc.getMessage());
            exc.printStackTrace();
        } catch (LengthMissMatchException exc) {
            LOGGER.error("Your try with not correct length.\n" + exc.getMessage());
            exc.printStackTrace();
        }
    }

    public void start() {
        out.println("Welcome to Bulls and Cows game!");
        while (true) {
            State state = runGame();
            printFinishMessage(state);
            if (isFinishedGame()) {
                break;
            }
        }
    }

    private boolean isFinishedGame() {
        try {
            out.println("Wanna play again? Y/N");
            String answer = reader.readLine();
            switch (answer) {
                case "Y":
                case "y":
                    return false;
                case "N":
                case "n":
                    return false;
                default:
                    throw new IllegalArgumentException("Wrong answer. We'll think answer is \"No\"");
            }
        } catch (IOException exc) {
            throw new InputMismatchException("Miss match");
        }
    }

    private void printFinishMessage(State state) {
        switch (state) {
            case WON:
                out.println("You won!");
                break;
            case LOST:
                out.println("You lost!");
                break;
            default:
                out.println("Unknown state");
        }
    }

    private State runGame() {
        try {
            String word = WordHelper.getRandomWord();
            greetingWord(word);

            for (int i = 0; i < TRY_COUNT; i++) {
                String answer = reader.readLine();
                if (word.equals(answer)) {
                    return State.WON;
                }
                out.println("Bulls: " + BullCounter.getBullCount(word, answer));
                out.println("Cows: " + CowCounter.getCowCount(word, answer));
            }

            return State.LOST;

        } catch (IOException exc) {
            throw new IllegalArgumentException("Your input word isn't correct");
        }
    }

    private void greetingWord(String word) {
        out.format("I offered a %d-letter word, your guess?%n", word.length());
    }
}
