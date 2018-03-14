package ru.atom;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class BullsAndCows implements Game {

    private String currentWord;
    private int triesNum;
    private boolean gameFinished;


    //i tried make it final but it's really comprehansive works  with try catch in constructor
    //so i just made set function
    private List<String> wordsList;

    private final int allowedAttemptsNum;
    private final Random randomize;



    public String getCurrentWord() {
        return currentWord;
    }

    public List<String> getWordsList() {
        return wordsList;
    }



    public boolean readAllLinesFromFile(String dictFilename) throws FileNotFoundException, IOException {
        File newFile = new File(dictFilename);
        List<String> resultList = new ArrayList<>();
        try (BufferedReader bufread = new BufferedReader(new FileReader(newFile))) {
            String newStr;
            while ((newStr = bufread.readLine()) != null) {
                resultList.add(newStr);
            }
            wordsList = resultList;
            return true;
        }
    }


    BullsAndCows(String dictFilename) throws IOException {
        allowedAttemptsNum = 10;
        randomize = new Random(System.currentTimeMillis());
        readAllLinesFromFile(dictFilename);
    }

    BullsAndCows(int attemptsNum, String dictFilename) throws IOException {
        allowedAttemptsNum = attemptsNum;
        randomize = new Random(System.currentTimeMillis());
        readAllLinesFromFile(dictFilename);
    }


    @Override
    public void startGame() {
        gameFinished = false;
        this.triesNum = 0;
        int randDictIndex = randomize.nextInt(wordsList.size());
        currentWord = wordsList.get(randDictIndex);
    }

    @Override
    public void restartGame() {
        startGame();
    }

    @Override
    public void finishGame() {
        gameFinished = true;
        return;
    }

    private int calcBulls(String word) {
        int bullsNum = 0;
        for (int i = 0 ; i < word.length(); i++) {
            if (word.charAt(i) == currentWord.charAt(i)) {
                bullsNum++;
            }
        }
        return bullsNum;
    }

    private int calcCows(String word) {
        int cowsNum = 0;
        StringBuffer sb =  new StringBuffer();
        HashMap<Character, Integer> hm = new HashMap<>();
        for (int i = 0 ; i < word.length(); i++) {
            if (word.charAt(i) != currentWord.charAt(i)) {
                if (hm.containsKey(currentWord.charAt(i))) {
                    hm.put(currentWord.charAt(i), hm.get(currentWord.charAt(i)) + 1);
                } else
                    hm.put(currentWord.charAt(i), 1);

                sb.append(word.charAt(i));
            }
        }
        word = sb.toString();
        for (int i = 0 ; i < word.length(); i++) {
            if (hm.containsKey(word.charAt(i))) {
                cowsNum++;
                if (hm.get(word.charAt(i)) > 1)
                    hm.put(word.charAt(i), hm.get(word.charAt(i)) - 1);
                else
                    hm.remove(word.charAt(i));
            }
        }
        return cowsNum;
    }

    public int[] makeTurn(String userTurn) {
        if (isGameFinished())
            return null;

        triesNum++;
        int[] result = new int[2];
        int numBulls = calcBulls(userTurn);
        int numCows = calcCows(userTurn);
        result[0] = numBulls;
        result[1] = numCows;
        if (numBulls == currentWord.length()) {
            gameFinished = true;
        }
        if (triesNum == allowedAttemptsNum)
            gameFinished = true;

        return result;
    }


    public boolean isGameFinished() {
        if (this.gameFinished)
            return true;
        else
            return false;
    }

    public boolean checkInputAnswer(String answer) {
        if (answer.length() != this.currentWord.length())
            return false;
        for (int i = 0; i < answer.length(); i++) {
            char temp = answer.charAt(i);
            if (!((temp >= 'a' && temp <= 'z') || (temp >= 'A' && temp <= 'Z')))
                return false;
        }
        return true;
    }

    @Override
    public boolean printByeMessage() {
        System.out.println("Thank's for the game! Bye! ");
        return true;
    }

    @Override
    public boolean printWelcome() {
        System.out.println("Welcome to Bulls and Cows game!");
        System.out.printf("I offered a %d-letter word. Your Guess?\n", currentWord.length());
        return true;
    }


    @Override
    public boolean printRules() {
        System.out.println("This is a Bulls and Cows game.");
        System.out.println("Here you have a limited number of attempts to guess which word was offered.");
        System.out.println("After every guess you made, the game will tell you how much bulls and cows " +
                "is in your word.");

        System.out.println("Here 1 bull means that there is 1 matching symbol in right position.");
        System.out.println("1 - cow means that there is matching symbols in different positions.");
        System.out.print("Basing on this answers you should try to guess which word was offered" +
                ", until you have enough attempts.");
        System.out.println("If you failed than you will lose. ¯\\_(ツ)_/¯");
        return false;
    }

    @Override
    public boolean printCongratulations() {
        System.out.println("************ Congratulations you win!!!! *********");
        return false;
    }

    @Override
    public boolean printLooseText() {
        System.out.println("############ Sorry this time you lost  :^) ##############");
        return false;
    }

}
