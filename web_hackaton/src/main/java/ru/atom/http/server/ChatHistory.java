package ru.atom.http.server;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mkai on 3/22/17.
 */
public class ChatHistory {

    public static void saveMessage(String msg) {
        File history = new File("./history.txt");
        if (history == null) {
            try {
                history.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(history, true); //the true will append the new data
            fw.write(msg + System.lineSeparator());//appends the string to the file
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }


    }

}
