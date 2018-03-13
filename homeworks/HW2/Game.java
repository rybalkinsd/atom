import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Status {INGAME, WIN, EXIT}

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
        //try (Scanner scanner = new Scanner(System.in)) {
        if (!scanner.hasNextLine()) {
            System.out.println("fuck");
        }
        ans = scanner.nextLine().toLowerCase();
        Pattern p = Pattern.compile("^[a-z]{" + secret.length() + "}$");
        Matcher m = p.matcher(ans);
        while (!m.matches()) {
            System.out.println("Your answer is incorrect (not in a - Z) try one more time" +
                    " or have wrong length (correct =" + secret.length() + ")");
            ans = scanner.nextLine().toLowerCase();
            m = p.matcher(ans);
        }
        return ans;
        // } catch (Throwable e) {
        //не разобрался с тем какие ексепшны кидает сканнер
        //     e.printStackTrace();
        //      return null;
        //  }
    }

    private String getNewWordFromDict() {
        Random r = new Random();
        final int MAX_WORD_IN_DICT = 52975;
        int rline = r.nextInt(MAX_WORD_IN_DICT) - 1;
        File file = new File("D:\\Dev\\Java_IDEA\\BullCow\\src\\dictionary.txt");
        String line;
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
        int i = 0;
        for (char el : truth) {
            if (el == sym) {
                truth[i] = '1';
                return true;
            }
            i++;
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
        char[] ans_ = ans.toCharArray();
        int bulls = giveBulls(ans_);
        int cows = giveCows(ans_) - bulls;
        return "U got " + cows + " Cows and " + bulls + " Bulls";
    }


}


