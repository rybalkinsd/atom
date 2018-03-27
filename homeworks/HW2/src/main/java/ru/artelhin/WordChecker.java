package ru.artelhin;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordChecker {
    private static Pattern p;
    private static Matcher m;
    private static int length;

    public static void patternSet(int len) {
        length = len;
        p = Pattern.compile("^[a-z]{" + length + "}$");
    }

    public static boolean wordCheck(String word) {
        m = p.matcher(word);
        if (m.matches())
            return true;
        System.out.println("Your word must consist only out of " + length + " lower case letters. Try again!");
        return false;
    }

    public static boolean answerCheck(String answer) {
        if (answer.equals("Y") || answer.equals("N"))
            return true;
        System.out.println("Your answer must be only 'Y' or 'N'. Try again!");
        return false;
    }

    private static void contains(HashMap<String, Integer> map, String key) {
        if (map.containsKey(key))
            map.put(key, map.get(key) + 1);
        else
            map.put(key,1);
    }

    public static boolean wordCompare(String word, String answer) {
        if (word.equals(answer))
            return true;
        String temp1;
        String temp2;
        int cows = 0;
        int bulls = 0;
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
        HashMap<String, Integer> answerMap = new HashMap<String, Integer>();
        for (int i = 0; i < word.length(); i++) {
            temp1 = word.substring(i,i + 1);
            temp2 = answer.substring(i,i + 1);
            if (temp1.equals(temp2))
                bulls++;
            else {
                contains(wordMap,temp1);
                contains(answerMap,temp2);
            }
        }
        for (String str : answerMap.keySet())
            if (wordMap.containsKey(str))
                cows += Math.min(answerMap.get(str),wordMap.get(str));
        System.out.println("Bulls:" + bulls);
        System.out.println("Cows:" + cows);
        return false;
    }
}
