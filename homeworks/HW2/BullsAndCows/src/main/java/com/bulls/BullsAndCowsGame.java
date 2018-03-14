package com.bulls;

import java.util.ArrayList;
import java.util.Scanner;

public class BullsAndCowsGame {
    public static final int MAX_GUESS_AMOUNT = 10;
    private static Scanner in = new Scanner(System.in);
    private static final String STOP_WORD = "QUIT";
    private static final String NEW_GAME = "WORD";

    // here we control players state
    private boolean playing = true;

    // using array to more comfortable guess handling
    private ArrayList<Character> word;

    // dictionary control our secret word
    private Dictionary dictionary = new Dictionary();
    private boolean playingRound;

    // new common class, what we use to return pair of bulls and cows
    class BullsAndCows {
        final int bulls;
        final int cows;

        BullsAndCows(int bulls, int cows) {
            this.bulls = bulls;
            this.cows = cows;
        }
    }

    // start our game
    public void play() {
        if(!init()) {
            System.out.println("Error occurred, but it doesnt mean, that we cant play together");
            System.out.println("Do you want to play with random words? (Y / N) ");
            if(!wannaPlay()) {
                leaving();
                return;
            }
        }

        greeting(); // or maybe some menu

        do {
            playRound();
        } while (isPlaying());

        leaving();
    }

    private void leaving() {
        System.out.println("See ya next time!");
    }

    private void greeting() {
        System.out.println("Welcome to Bulls and Cows game!");
        System.out.println("If you want to end game, just write: " + STOP_WORD);
        System.out.println("If you want to get new word, just write: " + NEW_GAME);
    }

    public boolean init() {
        return dictionary.init();
    }

    // we init fields what we change every round before it starts
    private void initRound() {
        initWord();
        startRound();
    }

    // we get random word from file
    private void initWord() {
        word = dictionary.getWord();
        System.out.println("I offered a " + word.size() + "-letter word, your guess?");
    }

    // here we get players guesses ad handle them
    private void playRound() {
        ArrayList<Character> guess;
        BullsAndCows guessResult;
        boolean won = false;

        initRound();

        for (int tries = 0; tries < MAX_GUESS_AMOUNT; tries ++) {
            System.out.println("Your " + (tries + 1) + " try: ");
            guess = getPlayerInput();
            if (roundStopped())
                return;

            guessResult = handleTry(guess);
            won = handleResult(guessResult);
            if (won)
                break;
            System.out.println("Bulls: " + guessResult.bulls);
            System.out.println("Cows: " + guessResult.cows);
        }
        if (won) {
            System.out.println("You won!");
        } else {
            System.out.println("You will be lucky next time!");
        }

        System.out.println("Wanna play again? Y/N");
        setPlaying(wannaPlay());
        if (isPlaying())
            System.out.println("So lets start again!");
    }

    // we ask and get players wish (play more or stop)
    // if we don`t understand what he want, we play again
    private boolean wannaPlay() {
        String wish = in.nextLine();
        if (handleCommands(wish))
            return true;

        if (wish.length() == 0) {
            wish = "Y";
        }

        switch (wish.charAt(0)) {
            case 'Y':
            case 'y':
                return true;
            case 'N':
            case 'n':
                return false;
            default:
                if (wish.contains("Yes") || wish.contains("Yes"))
                    return true;
                if (wish.contains("No") || wish.contains("no")) {
                    return false;
                }
                System.out.println("I don`t understand you, but i think you wanna play more");
                System.out.println("If you want to end this game, just write: " + STOP_WORD);
                return true;
        }
    }

    // we handle players input
    // we skip symbols what are not letters
    private ArrayList<Character> getPlayerInput() {
        String string;
        ArrayList<Character> playersTry = new ArrayList<>();

        do {
            string = in.nextLine();
            if (handleCommands(string)) {
                return new ArrayList<>();
            }
            for (Character character : string.toCharArray()) {
                if (character.compareTo('A') >= 0 && character.compareTo('Z') <= 0)
                    character = (char) (character - 'A' + 'a');
                if (character.compareTo('a') >= 0 && character.compareTo('z') <= 0)
                    playersTry.add(character);
            }
            if (playersTry.size() == 0) {
                System.out.println("I can`t use this word, write another one.");
                continue;
            }
            if (playersTry.size() != word.size()) {
                System.out.println("Write word with same size as i imagined.");
                playersTry = new ArrayList<>();
            }
        } while (playersTry.size() == 0);

        return playersTry;
    }

    // now we have only 2 special command
    // "STOP" - stops whole game
    // "WORD" - starts new round
    private boolean handleCommands(String command) {
        switch (command) {
            case STOP_WORD:
                stopGame();
                return true;
            case NEW_GAME:
                stopRound();
                return true;
            default:
                return false;
        }
    }

    // we check if player stopped round by some command
    private boolean roundStopped() {
        return !playingRound;
    }

    // if player don`t want to continue, or some command was called
    private void stopGame() {
        stopRound();
        setPlaying(false);
    }

    // call if we wanna start new round
    private void stopRound() {
        playingRound = false;
    }

    private void startRound() {
        playingRound = true;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    // we count how much bulls and cows we have
    private BullsAndCows handleTry(ArrayList<Character> guess) {
        int bulls = 0;
        int cows = 0;
        int slider;
        ArrayList<Character> secret = new ArrayList<>(word);

        // at first we count bulls
        slider = 0;
        while (slider < guess.size() && slider < secret.size()) {
            if (guess.get(slider) == secret.get(slider)) {
                guess.remove(slider);
                secret.remove(slider);
                bulls++;
                continue;
            }
            slider ++;
        }

        // then cows
        slider = 0;
        while (slider < guess.size() && secret.size() > 0) {
            int found = secret.indexOf(guess.get(slider)) ;
            if (found != -1) {
                secret.remove(found);
                cows++;
            }
            slider++;
        }
        // return special pair
        return new BullsAndCows(bulls, cows);
    }

    // now we have to understand, player won, or not
    private boolean handleResult(BullsAndCows guessResult) {
        if (guessResult.bulls == word.size()) {
            return true;
        }
        if (guessResult.cows == word.size()) {
            System.out.println("Nice try!");
        }
        return false;
    }
}
