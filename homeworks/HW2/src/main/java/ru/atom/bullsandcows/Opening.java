package ru.atom.bullsandcows;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Opening {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Opening.class);

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Scanner dictScanner;
        InputStream stream;

        try {
            stream = Opening.class.getClassLoader().getResourceAsStream("dictionary.txt");
            dictScanner = new Scanner(stream);
            while (dictScanner.hasNext()) {
                list.add(dictScanner.next());
            }
        } catch (NullPointerException ex) {
            log.warn("method invoked through null-pointer variable.Maybe,your dictionary is missing");
            return;
        }
        MainGameCycle.start(list);
    }


}
