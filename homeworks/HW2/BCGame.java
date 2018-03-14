package bulls;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class BCGame {
    private String word = null;
    private int len = 0;
    private short [] let = new short [26];

    public int getLen() {
        return len;
    }

    //counts number of lines in dictionary.txt file
    private int count_lines() throws Exception {
        int res = 0;
        FileReader fr;
        Scanner scan;
        fr = new FileReader( "dictionary.txt" );
        scan = new Scanner(fr);
        while(scan.hasNextLine()) {
            scan.nextLine();
            res++;
        }
        fr.close();
        return res;
    }

    //finds random word in dictionary.txt file
    boolean find_word(){
        try {
            int a = (int) (Math.random() * count_lines());

            FileReader fr;
            Scanner scan;
            fr = new FileReader( "dictionary.txt" );
            scan = new Scanner(fr);

            for (int i = 0; i < a; i++) {
                if (scan.hasNextLine()) {
                    scan.nextLine();
                } else {
                    System.out.println("Failed to find word");
                    return false;
                }
            }

            word = scan.nextLine();
            len = word.length();
            fr.close();

            for (int i = 0; i < len; i++) {
                let[word.charAt(i) - 'a']++;
            }

            //System.out.println("By the way, word is " + word);
        }
        catch (FileNotFoundException e) {
            System.out.println("Failed to find dictionary");
            return false;
        }
        catch (Exception e) {
            System.out.println("An error occured" + e);
            return false;
        }
        return true;
    }


    // returns status:
    // 0 if player did not guess
    // 1 if the word is correct
    // 2 if player did not follow the rules (wrong length or symbols)
    int cmp(String guess) {
        if (guess == null) {
            System.out.println("Type a word to guess");
            return 2;
        }
        if (len != guess.length()) {
            System.out.println("Wrong length. Try again");
            return 2;
        }

        guess = guess.toLowerCase();
        for (int i = 0; i < len; i++) {
            if((guess.charAt(i) < 'a') || (guess.charAt(i) > 'z')){
                System.out.println("Invalid symbols (use only a..z). Try again");
                return 2;
            }
        }

        int bulls = 0, cows = 0;

        for (int i = 0; i < len; i++) {
            if (word.charAt(i) == guess.charAt(i)) {
                bulls++;
            }
        }

        if (bulls == len) {
            return 1;
        }

        short [] table = new short [26];

        for (int i = 0; i < len; i++) {
            table[guess.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            cows += Math.min(let[i], table[i]);
        }
        cows -= bulls;

        System.out.println("Good guess. There are:\n" + cows + " cows\n" + bulls + " bulls\n");
        return 0;
    }
}
