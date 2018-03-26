package ru.atom.chat;

import java.io.*;

public class HistorySaver {
    private OutputStream file;

    public HistorySaver() {
        try {
            file = new FileOutputStream(new File("history.txt"), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveHistory(String history) {
        try {
            history += " <br /> ";
            file.write(history.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHistory() {
        InputStream is;
        String result = null;
        try {
            is = new FileInputStream("history.txt");
            result = new String(is.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
