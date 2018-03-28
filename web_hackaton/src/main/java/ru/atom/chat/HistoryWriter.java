package ru.atom.chat;

import ru.atom.chat.message.IMessage;
import ru.atom.chat.message.Message;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HistoryWriter {

    private File newFile;
    private BufferedWriter bw;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HistoryWriter.class);

    HistoryWriter(String filename) throws IOException {
        newFile = new File(filename);
        newFile.createNewFile();
    }


    public void saveHistory(Queue<IMessage> q) {
        try (BufferedWriter newBW =  new BufferedWriter(new FileWriter(newFile))) {
            for (IMessage mes: q) {
                newBW.write(mes.getUserName());
                newBW.newLine();
                newBW.write(mes.getMessageBody());
                newBW.newLine();
                newBW.write(mes.getTime());
                newBW.newLine();
            }
        } catch (IOException ioe) {
            log.error("Fatal IOException error");
        }
    }
}
