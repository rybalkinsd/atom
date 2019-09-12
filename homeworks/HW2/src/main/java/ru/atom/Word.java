package main.java.ru.atom;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Word {

    private static File file = new File("dictionary.txt");

    public static String cowsAndBulls() {
        String out="";
        try(Stream<String> streamFromFiles = Files.lines(Paths.get("dictionary.txt"))) {
            out=streamFromFiles.skip((int) (Math.random() * 52976)).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}