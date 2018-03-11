package ru.atom.bullsandcows;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordPatternChecker {

    private static Pattern p;
    private static Matcher m;
    private static int curLength;


    public static void setPattern(int len) {
        curLength = len;
        p = Pattern.compile("^[a-z]{" + len + "}$");
    }


    public static boolean wordCheck(String word) {
        m = p.matcher(word);
        if (m.matches()) {
            return true;
        }
        System.out.println("Your word must match the pattern ^[a-z]{" + curLength +
                "}$ Try once more:");
        return false;
    }


    public static boolean assertCheck(String assrt) {
        if (assrt.equals("Y") || assrt.equals("N"))
                return true;
        System.out.println("Your answer should be 'Y' or 'N'! Try once more:");
        return false;
    }

    private static void containingChecker(HashMap<String,Integer> map , String key) {
        if (map.containsKey(key))
            map.put(key,map.get(key) + 1);
        else
            map.put(key,1);
    }

    public static boolean ansComparer(String word , String ans) {
        if (word.equals(ans))
            return true;
        String tmp1;
        String tmp2;
        int cowsCtr = 0;
        int bullsCtr = 0;
        HashMap<String,Integer> wordMap = new HashMap<>();
        HashMap<String,Integer> ansMap = new HashMap<>();
        for (int i = 0;i < word.length();i++) {
            tmp1 = word.substring(i,i + 1);
            tmp2 = ans.substring(i,i + 1);
            if (tmp1.equals(tmp2))
                bullsCtr++;
            else {
                containingChecker(wordMap,tmp1);
                containingChecker(ansMap,tmp2);
            }
        }
        for (String str : ansMap.keySet())
            if (wordMap.containsKey(str))
                cowsCtr += Math.min(ansMap.get(str),wordMap.get(str));
        System.out.println("Bulls:" + bullsCtr);
        System.out.println("Cows:" + cowsCtr);
        return false;
    }


}
