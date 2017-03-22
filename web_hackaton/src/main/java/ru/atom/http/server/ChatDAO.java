package ru.atom.http.server;

import org.eclipse.jetty.util.ConcurrentArrayQueue;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by dmbragin on 3/22/17.
 */
public class ChatDAO {
    private File targetFile;
    private OutputStream outStream;
    private InputStream inputStream;

    public ChatDAO(String filePath) {
        this.targetFile = new File(filePath);
        try {
            this.outStream = new FileOutputStream(targetFile, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.inputStream = new FileInputStream(targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(String msg) {
        try {
            String tmp = msg + "\n";
            outStream.write(tmp.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConcurrentArrayQueue<String> getAll() {
        ConcurrentArrayQueue<String> logined = new ConcurrentArrayQueue<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                logined.offer(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logined;
    }
}
