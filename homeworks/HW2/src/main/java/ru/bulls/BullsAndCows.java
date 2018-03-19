package ru.bulls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BullsAndCows {

    private final ArrayList<String> words;
    private final Random a;
    private String current;

    public BullsAndCows() {
        words = new ArrayList<>();
        a = new Random();
    }

    public String getCurrent() {
        return current;
    }

    public void setWords(String filename) throws IOException {
        words.clear();
        InputStream inputStream = BullsAndCows.class.getClassLoader().getResourceAsStream(filename);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                words.add(word);
            }
        }
    }

    public String nextWord() {
        current = words.get(a.nextInt(words.size()));
        return current;
    }

    public Result game(String word) {
        if (word.length() != current.length()) throw new IllegalArgumentException();

        int bulls = 0;
        int caws = 0;

        List<Character> slovo = current.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        List<Character> slovoPlayer = word.chars().mapToObj(c -> (char)c).collect(Collectors.toList());

        for (int i = 0; i < word.length(); i++) {
            if (current.charAt(i) == word.charAt(i)) {
                slovo.remove(i - bulls);
                slovoPlayer.remove(i - bulls);
                bulls++;
            }
        }
        for (char a : slovo) {
            if (slovoPlayer.contains(a)) {
                slovoPlayer.remove(Character.valueOf(a));
                caws ++;
            }
        }
        return new Result(bulls,caws);
    }

    public class Result {

        private final int bulls;
        private final int caws;

        public int getBulls() {
            return bulls;
        }

        public int getCaws() {
            return caws;
        }

        Result(int b, int c) {
            this.bulls = b;
            this.caws = c;
        }
    }

}
