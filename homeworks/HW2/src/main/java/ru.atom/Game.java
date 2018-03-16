package ru.atom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Game {

    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionHandler.class);
    private String secret;
    private Status status;
    private int counts;

    Game(int c) {
        this.counts = c;
        this.status = Status.INGAME;
    }

    public int getCounts() {
        return counts;
    }

    public void setStatus(Status s) {
        this.status = s;
    }

    public Status getStatus() {
        return status;
    }

    public int getSecretLen() {
        return secret.length();
    }

    public String getSecret() {
        return secret;
    }

    public void newWord() {
        secret = getNewWordFromDict();
    }

    String getNewAnswer(Scanner scanner) {
        String ans;


        ans = scanner.nextLine().toLowerCase();
        Pattern pattern = Pattern.compile("^[a-z]{" + secret.length() + "}$");
        Matcher matcher = pattern.matcher(ans);
        while (!matcher.matches()) {
            System.out.println("Your answer is incorrect (not in a - Z) try one more time" +
                    " or have wrong length (correct =" + secret.length() + ")");
            ans = scanner.nextLine().toLowerCase();
            matcher = pattern.matcher(ans);
        }
        return ans;

    }

    private String getNewWordFromDict() {
        Random random = new Random();
        final int Max = 52975;
        int rline = random.nextInt(Max) - 1;
        //URL url = getClass().getResource("diсtionary.txt");
        String path = this.getClass().getResource("/dictionary.txt").getPath();

        File file = new File(path);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < rline; i++)
                bufferedReader.readLine();
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int giveCows(char[] ans) {
        char[] truth = getSecret().toCharArray();
        int count = 0;
        for (char sym : ans)
            if (isinArrayChange(sym, truth))
                count++;

        return count;
    }

    private boolean isinArrayChange(char sym, char[] truth) {
        int inc = 0;
        for (char el : truth) {
            if (el == sym) {
                truth[inc] = '1';
                return true;
            }
            inc++;
        }
        return false;
    }

    private int giveBulls(char[] ans) {
        char[] truth = getSecret().toCharArray();
        int count = 0;
        for (int i = 0; i < getSecretLen(); i++)
            if (truth[i] == ans[i])
                count++;
        return count;


    }

    public String giveCowsnBulls(String ans) {
        if (ans.equals(getSecret())) {
            setStatus(Status.WIN);
            return "Congratulations U Win!";
        }
        char[] ansChar = ans.toCharArray();
        int bulls = giveBulls(ansChar);
        int cows = giveCows(ansChar) - bulls;
        return "U got " + cows + " Cows and " + bulls + " Bulls";
    }


}


