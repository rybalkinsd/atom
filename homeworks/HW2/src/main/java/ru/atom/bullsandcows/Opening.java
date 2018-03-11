package ru.atom.bullsandcows;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Opening {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Opening.class);

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        String path;
        Scanner dictScanner;

        try {
            path = Opening.class.getClassLoader().getResource("dictionary.txt").getPath();
            dictScanner = new Scanner(new File(path));
            while (dictScanner.hasNext()) {
                list.add(dictScanner.next());
            }
        } catch (NullPointerException ex) {
            log.warn("method invoked through null-pointer variable.Maybe,your dictionary is missing");
            return;
        } catch (FileNotFoundException ex) {
            log.warn("File not found!Check your resources.");
            return;
        }

        MainGameCycle.start(list);
    }


}
