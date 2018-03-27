package ru.artelhin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class BullsAndCows {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BullsAndCows.class);

    public static void main(String[] args) {

        InputStream stream;
        ArrayList<String> list = new ArrayList<String>();
        Scanner reader;

        try {
            stream = BullsAndCows.class.getClassLoader().getResourceAsStream("dictionary.txt");
            reader = new Scanner(stream);
            while (reader.hasNext()) {
                list.add(reader.next());
            }

        } catch (NullPointerException exep) {
            log.warn("Null-pointer variable used as parameter. Dictionary may be missing.");
            return;
        }

        GameSession.handleGame(list);
    }
}
