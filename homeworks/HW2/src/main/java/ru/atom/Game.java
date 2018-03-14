package ru.atom;

public interface Game {

    void startGame();

    void restartGame();

    void finishGame();

    boolean printWelcome();

    boolean printByeMessage();

    boolean printRules();

    boolean printCongratulations();

    boolean printLooseText();

}

