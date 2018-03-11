package com.bulls;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static final int MAX_GUESS_AMOUNT = 10;

    private boolean playing = true;
    private boolean won;

    private ArrayList<Character> word;
    Dictionary dictionary = new Dictionary();

    class BullsAndCows {
        final int bulls;
        final int cows;
        BullsAndCows(int bulls, int cows) {
            this.bulls = bulls;
            this.cows = cows;
        }

    }

    public void play () {
        System.out.println("Welcome to Bulls and Cows game!");
        dictionary.init();

        do {
            initWord();
            startRound();
            playMore();
        } while (playing);

        System.out.println("See ya next time!");
    }

    private void playMore() {
        Scanner in = new Scanner(System.in);
        System.out.println("Wanna play again? Y/N");
        switch (in.nextLine()) {
            case "Y": case"y":
                playing = false;
                break;
            case "N": case"n":
                playing = false;
                break;
            default:
                break;
        }
    }

    private void startRound() {
        Scanner in = new Scanner(System.in);
        ArrayList<Character> guess;
        BullsAndCows guessResult;
        for(int tries = 0; tries < MAX_GUESS_AMOUNT; tries ++) {
            guess = getPlayerInput(in);
            guessResult = handleTry(guess);
            handleResult(guessResult);
            if(won) {
                System.out.println("You won!");
                return;
            }
            System.out.println("Bulls: " + guessResult.bulls);
            System.out.println("Cows: " + guessResult.cows);
        }
        System.out.println("You will be lucky next time!");
    }

    private ArrayList<Character> getPlayerInput(Scanner in) {
        String string = in.nextLine();
        ArrayList<Character> playersTry = new ArrayList<>();
        for(Character character :string.toCharArray())
            playersTry.add(character);
        return playersTry;
    }

    private void handleResult(BullsAndCows guessResult) {
        if (guessResult.bulls == word.size()) {
            won = true;
            return;
        }
        if (guessResult.cows == word.size()) {
            System.out.println("Nice try!");
        }
        won = false;
    }

    private BullsAndCows handleTry(ArrayList<Character> guess) {
        int bulls = 0;
        int cows = 0;
        int i;
        ArrayList<Character> secret = new ArrayList<>(word);

        i = 0;
        while (i < guess.size() && i < secret.size()) {
            if(guess.get(i) == secret.get(i)) {
                guess.remove(i);
                secret.remove(i);
                bulls++;
                continue;
            }
            i ++;
        }

        i = 0;
        while (i < guess.size() && secret.size() > 0) {
            int found = secret.indexOf(guess.get(i)) ;
            if(found != -1) {
                secret.remove(found);
                cows++;
            }
            i++;
        }
        return new BullsAndCows(bulls, cows);
    }

    private void initWord() {
        word = dictionary.getWord();
        System.out.println("I offered a " + word.size() + "-letter word, your guess?");
    }
}
