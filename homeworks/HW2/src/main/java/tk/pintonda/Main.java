package tk.pintonda;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {
    List<String> words = new ArrayList<>();
    private String word;
    private String userWord;
    static boolean gameIsOn = true;
    static boolean userWin;
    Scanner scnIn;

    public static void main(String[] args){
        startGame();
    }

    public static void startGame(){
        Main main = new Main();
        main.setWord(main.readFile());
        while (gameIsOn){
            main.greetings(main.getWord().length());
            int tries = 0;
            while (tries < 10){
                main.gettingInputWord();
                main.checkWord();
                if (userWin) {
                    break;
                }
                tries++;
            }
            if (userWin) {
                System.out.println("You won!");
            }   else {
                System.out.println("You lose! Word was: " + main.getWord());
            }
            System.out.println("Wanna play again? Y/N");
            while (main.askIsGameIsOn());
        }
        main.scnIn.close();
    }

    public String readFile(){
        try (Scanner scn = new Scanner(Main.class.getClassLoader().getResourceAsStream("dictionary.txt"))){
            while (scn.hasNextLine()) {
                words.add(scn.nextLine());
            }
        } catch (NullPointerException e) {
            System.out.println("File not found");
        }

        return words.get(new Random().nextInt(words.size()));
    }

    public boolean askIsGameIsOn() {
        String yN = scnIn.nextLine().toLowerCase();
        if (yN.equals("n")){
            gameIsOn = false;
        }   else if (yN.equals("y")) {
            // Do nothing, gameIsOn is true by default
        }   else {
            System.out.println("Incorrect input. Try again");
            return true;
        }
        return false;
    }

    public void greetings(int length){
        System.out.println("Welcome to Bull and Cows game!\n I offered a " + length + "-letter word, your guess?");
    }

    public void gettingInputWord(){
        scnIn = new Scanner(System.in);
        setUserWord(scnIn.nextLine());
        while (getUserWord().length() != getWord().length() ||
                !getUserWord().matches("[a-zA-Z]+")){
            System.out.println("Cant read a word or word length is incorrect. Try again");
            setUserWord(scnIn.nextLine());
        }
    }

    public void checkWord(){
        int bulls = 0;
        int cows = 0;

        if (getUserWord().equals(getWord())){
            userWin = true;
            return;
        }

        char[] uWord = getUserWord().toCharArray();
        char[] inWord = getWord().toCharArray();



        List<Character> arrInWord = new ArrayList<>();
        Set<Character> setUWord = new TreeSet<>();

        for (char ch: inWord) {
            arrInWord.add(ch);
        }

        for (char ch: uWord) {
            setUWord.add(ch);
        }

        for (int j = 0; j < inWord.length; j++){

            for (int i = 0; i <uWord.length; i++) {
                if (i == j && uWord[i] == inWord [j]){
                    bulls++;
                }
            }

            Iterator<Character> itU = setUWord.iterator();
            while (itU.hasNext()){
                if (itU.next() == inWord[j]){
                    cows++;
                }
            }
        }

        cows = cows - bulls;

        System.out.println("Bulls: " + bulls);
        System.out.println("Cows: " + cows);
    }

    public String getUserWord() {
        return userWord;
    }

    public void setUserWord(String userWord) {
        this.userWord = userWord.toLowerCase();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
