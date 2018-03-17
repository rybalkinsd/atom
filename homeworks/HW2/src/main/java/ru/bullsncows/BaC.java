package ru.bullsncows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BaC {
    private final List<String> words;
    private final Random r;
    private String current;

    public BaC() {
        words = new ArrayList<>();
        r = new Random();
    }

    void setCurrent(String s) {
        current = s;
    }

    public void setWords(String resourceName)
            throws IOException {
        words.clear();
        InputStream inputStream = BaC.class.getClassLoader().getResourceAsStream(resourceName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        }
    }

    public String nextWord() {
        current = words.get(r.nextInt(words.size()));
        return current;
    }

    public Overlap getOverlap(String word) {
        if (word.length() != current.length()) throw new IllegalArgumentException();
        int cows = 0;
        int bulls = 0;
        List<Character> rightChars = current.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
        List<Character> testChars = word.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
        for (int i = 0; i < current.length(); i++) {
            if (current.charAt(i) == word.charAt(i)) {
                rightChars.remove(i - bulls);
                testChars.remove(i - bulls);
                bulls++;
            }
        }
        for (char ch : testChars) {
            if (rightChars.contains(ch)) {
                cows++;
                rightChars.remove(Character.valueOf(ch));
            }
        }
        return new Overlap(cows,bulls);
    }

    public class Overlap {
        private final int c;
        private final int b;

        public int getC() {
            return c;
        }

        public int getB() {
            return b;
        }

        Overlap(int c, int b) {
            this.c = c;
            this.b = b;
        }
    }
}
