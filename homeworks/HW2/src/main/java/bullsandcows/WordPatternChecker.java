package bullsandcows;


import java.util.HashSet;
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
        HashSet<String> tmp = new HashSet<String>();
        for (int i = 0;i < word.length();i++)
            tmp.add(word.substring(i,i + 1));
        if (m.matches() && tmp.size() == word.length()) {
            return true;
        }
        System.out.println("Your word must match the pattern ^[a-z]{" + curLength +
                "}$ and contain different letters! Try once more:");
        return false;
    }


    public static boolean assertCheck(String assrt) {
        if (assrt.equals("Y") || assrt.equals("N"))
                return true;
        System.out.println("Your answer should be 'Y' or 'N'! Try once more:");
        return false;
    }


    public static boolean ansComparer(String word , String ans) {
        if (word.equals(ans))
            return true;
        int cowsCtr = 0;
        int bullsCtr = 0;
        HashSet<String> wordSet = new HashSet<String>();
        HashSet<String> ansSet = new HashSet<String>();
        for (int i = 0;i < word.length();i++) {
            if (word.substring(i,i + 1).equals(ans.substring(i,i + 1)))
                bullsCtr++;
            else {
                wordSet.add(word.substring(i,i + 1));
                ansSet.add(ans.substring(i,i + 1));
            }
        }
        for (String str : ansSet)
            if (wordSet.contains(str))
                cowsCtr++;
        System.out.println("Bulls:" + bullsCtr);
        System.out.println("Cows:" + cowsCtr);
        return false;
    }


}
