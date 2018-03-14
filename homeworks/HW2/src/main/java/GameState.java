import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


class GameState {
    private ArrayList<String> dict;
    private String curWord;
    private Scanner scanner;
    private Random rand;
    private String pattern;
    private int curAttempt;

    GameState(ArrayList<String> dict) {
        this.dict = dict;
        this.scanner = new Scanner(System.in);
        this.rand = new Random();
    }

    public void startGame() {
        System.out.println("Welcome to Bulls and Cows game!");
        boolean exit = false;
        while (!exit) {
            this.startSession();
            System.out.print("Wanna play again? Y/N\n > ");
            String ans = this.scanner.nextLine();
            if (ans.equals("Y")) {
                continue;
            } else if (ans.equals("N")) {
                exit = true;
            } else {
                System.out.println("Wrong input.");
                exit = true;
            }
        }
    }

    private void startSession() {
        this.curWord = dict.get(this.rand.nextInt(this.dict.size()));
        this.pattern = String.format("[a-z]{%d}+", this.curWord.length());
        this.curAttempt = 1;
        System.out.print(String.format("I offered a %d-letter word, your guess?\n > ",
                         this.curWord.length()));
        while (this.curAttempt <= 10) {
            String word = this.scanner.nextLine();
            if (!this.checkWord(word)) {
                System.out.print("Wrong input.\n > ");
                continue;
            }

            ArrayList<Integer> bnc = this.getCowsNBulls(word);
            int bulls = bnc.get(0);
            int cows = bnc.get(1);
            this.curAttempt++;
            if (bulls  == this.curWord.length()) {
                System.out.println("You won!");
                return;
            }

            System.out.print(String.format("Bulls: %d\nCows: %d\n > ", bnc.get(0), bnc.get(1)));
        }
        System.out.print(String.format("You lost! The word was %s.\n", this.curWord));
    }

    private boolean checkWord(String s) {
        return s.matches(this.pattern);
    }

    private ArrayList<Integer> getCowsNBulls(String s) {
        ArrayList<Integer> ans = new ArrayList<Integer>(2);
        int bulls = 0;
        int cows = 0;
        ArrayList<Character> wordWrong = new ArrayList<>();
        ArrayList<Character> stWrong = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == this.curWord.charAt(i))
                bulls++;
            else {
                wordWrong.add(this.curWord.charAt(i));
                stWrong.add(s.charAt(i));
            }
        }
        for (int i = 0; i < stWrong.size(); i++) {
            if (wordWrong.indexOf(stWrong.get(i)) != -1) {
                cows++;
                wordWrong.remove(stWrong.get(i));
            }
        }

        ans.add(bulls);
        ans.add(cows);
        return ans;
    }
}