package atom.game;


import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.io.IOException;
// http://www.igrovaya.ru/detail.php?ID=2143

public class BullsAndCows {
    private final org.slf4j.Logger log = org.slf4j
                                            .LoggerFactory
                                            .getLogger(BullsAndCows.class);
    private int currAttempt;
    private int maxAttempts;
    private String dictPath;
    private String secretWord;
    private ArrayList<String> words;
    private HashMap<Character, Integer> wordMap;
    int bulls;
    int cows;

    public BullsAndCows(int maxAttempts, String dictPath) {
        currAttempt = 0;
        this.maxAttempts = Math.max(maxAttempts, 1);
        this.dictPath = dictPath;
        secretWord = "";
        words = new ArrayList<String>();
        wordMap = new HashMap<Character, Integer>();
        bulls = 0;
        cows  = 0;
    }

    public int getSecretWordLength() {
        return secretWord.length();
    }

    public boolean isExhausted() {
        return currAttempt == maxAttempts;
    }

    public void reset() {
        cows = 0;
        bulls = 0;
        currAttempt = 0;
        wordMap.clear();
    }

    public boolean isWin() {
        return (currAttempt <= maxAttempts) && bulls == secretWord.length();
    }

    public boolean isEnd() {
        return isWin() || isExhausted();
    }

    public int getBulls() {
        return this.bulls;
    }

    public int getCows() {
        return this.cows;
    }

    public void init() throws BullsAndCowsException {
        BufferedReader bufferedReader = null;
        try {
            File file = new File(dictPath);
            bufferedReader = new BufferedReader(new FileReader(file));
            String currLine = null;
            while ((currLine = bufferedReader.readLine()) != null) {
                words.add(currLine);
            }
            if (words.size() == 0)
                throw new BullsAndCowsException("There are not any words in dictionary file.");
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException in init, filename = {}", dictPath);
            throw new BullsAndCowsException(e);
        } catch (IOException e) {
            log.error("IOException in init, filename = {}", dictPath);
            throw new BullsAndCowsException(e);
        } catch (BullsAndCowsException e) {
            log.error("BullsAndCowsException in init.");
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public void start() throws BullsAndCowsException {
        if (words.size() == 0) {
            log.error("There are not any words in internal dictionary.");
            throw new BullsAndCowsException();
        }

        int randInt = (int) (Math.random() * words.size());
        secretWord = words.get(randInt);
        int tmp = 1;
        for (int i = 0; i < secretWord.length(); ++i) {
            char symbol = secretWord.charAt(i);
            tmp = wordMap.containsKey(symbol)
                  ? wordMap.get(symbol) : 0;
            wordMap.put(symbol, ++tmp);
        }
        // System.out.println("Secret word -> " + secretWord);
        // System.out.print("wordMap -> ");
        // System.out.println(wordMap);
    }

    public void loop() throws BullsAndCowsException {
        try {
            while (true) {
                if (isEnd()) {
                    if (isWin()) {
                        System.out.println("You won!");
                    } else if (isExhausted()) {
                        System.out.println("Sorry, you are lose.");
                        System.out.println("Secret word -> " + secretWord);
                    }

                    if (!startNewGame()) {
                        System.out.println("Good bye!");
                        break;
                    }

                    reset();
                    start();
                    System.out.println("I offered a "
                                        + Integer.toString(getSecretWordLength())
                                        + "-letter word, your guess?");
                } else {
                    String userWord = getNextUserWord();
                    if (userWord == null)
                        continue;

                    if (!step(userWord))
                        continue;

                    System.out.println("Bulls: " + Integer.toString(getBulls()));
                    System.out.println("Cows: " + Integer.toString(getCows()));
                }
            }
        } catch (BullsAndCowsException e) {
            throw e;
        }
    }

    public String getNextUserWord() throws BullsAndCowsException {
        currAttempt += 1;
        String result = null;
        Scanner in = null;
        try {
            in = new Scanner(System.in);
            System.out.print("Enter next variant: ");
            result = in.nextLine();
            if (!checkUserWord(result))
                result = null;
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException in getNextUserWord.");
            throw new BullsAndCowsException(e);
        } catch (IllegalStateException e) {
            log.error("IllegalStateException in getNextUserWord.");
            throw new BullsAndCowsException(e);
        }

        return result;
    }

    public boolean checkUserWord(String userWord) {
        for (int i = 0; i < userWord.length(); ++i) {
            if (userWord.charAt(i) < 'a' || userWord.charAt(i) > 'z') {
                log.error("User word contain not only lower latin characters");
                return false;
            }
        }
        return true;
    }

    public boolean step(String userWord) {
        if (userWord.length() != secretWord.length()) {
            log.error("User word is not the same length than secret word");
            return false;
        }

        this.bulls = 0;
        this.cows = 0;
        int delta;
        for (int i = 0; i < userWord.length(); ++i) {
            char symbol = userWord.charAt(i);
            delta = (symbol == secretWord.charAt(i)) ? 1 : 0;
            this.bulls += delta;
            if (wordMap.containsKey(symbol) && wordMap.get(symbol) >= (1 + delta)) {
                this.cows += (wordMap.get(symbol) - delta);
            }
        }
        return true;
    }

    public boolean startNewGame() throws BullsAndCowsException {
        boolean result = false;
        Scanner in = null;
        try {
            in = new Scanner(System.in);
            System.out.print("Wanna play again? Y/N: ");
            String answer = in.nextLine();
            result = (answer.equals("Y")) ? true : false;
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException in startNewGame.");
            throw new BullsAndCowsException(e);
        } catch (IllegalStateException e) {
            log.error("IllegalStateException in startNewGame.");
            throw new BullsAndCowsException(e);
        }

        return result;
    }

    public static <T> void printArray(T[] array) {
        for (int i = 0; i < array.length; ++i) {
            System.out.println(array[i]);
        }
    }

    public static void main(String[] args) {
        Config config = null;
        config = (args.length == 0) ? new Config() : new Config(args[0]);

        int maxAttempts = 0;
        String dictPath = "";
        try {
            config.load();
            maxAttempts = config.getIntByName("MAX_ATTEMPTS");
            dictPath = config.getStringByName("DICT_PATH");
        } catch (BullsAndCowsException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        BullsAndCows game = new BullsAndCows(maxAttempts, dictPath);
        try {
            game.init();
            game.start();
            System.out.println("Welcome to Bulls and Cows game");
            System.out.println("I offered a "
                                + Integer.toString(game.getSecretWordLength())
                                + "-letter word, your guess?");
        } catch (BullsAndCowsException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        try {
            game.loop();
        } catch (BullsAndCowsException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
