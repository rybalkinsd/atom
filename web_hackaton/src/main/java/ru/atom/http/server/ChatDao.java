package ru.atom.http.server;

import org.eclipse.jetty.util.ConcurrentArrayQueue;

import java.io.InputStreamReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by dmbragin on 3/22/17.
 */
public class ChatDao {
    private File targetFile;
    private OutputStream outStream;
    private InputStream inputStream;

    public ChatDao(String filePath) {
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

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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
