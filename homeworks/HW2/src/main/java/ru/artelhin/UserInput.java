package ru.artelhin;

import java.util.Scanner;

public class UserInput {

    private static Scanner input = new Scanner(System.in);

    public static boolean answerInput() {
        String str;
        do {
            str = input.next();
        } while (!(WordChecker.answerCheck(str)));
        return str.equals("Y");
    }

    public static String wordInput() {
        String str;
        do {
            str = input.next();
        } while (!(WordChecker.wordCheck(str)));
        return str;
    }

}
