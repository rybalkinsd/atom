package ru.atom;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import static java.lang.Math.random;

public class SecretWord {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SecretWord.class);


    private String word;

    private String dictionaryName = "dictionary.txt";
    private int countLines;

    public String getWord() {
        return word;
    }

    public SecretWord() {
        countingLines();
    }

    private InputStream getPathDictionary() {
        return getClass().getClassLoader().getResourceAsStream(dictionaryName);
    }

    private void countingLines() {
        try (LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(getPathDictionary()))) {
            while (lineNumberReader.readLine() != null) {
                countLines++;
            }
        } catch (IOException e) {
            log.warn(e.toString());
        }
    }

    public void guessWord() {
        int randLine = (int) (random() * countLines);
        String currentLine;

        try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(getPathDictionary()))) {

            while ((currentLine = reader.readLine()) != null) {
                if (reader.getLineNumber() == randLine) {
                    word = currentLine;
                    log.warn(word);
                    break;
                }
            }
        } catch (IOException e) {
            log.warn(e.toString());
        }
    }
}
