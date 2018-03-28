package ru.atom.chat;

import ru.atom.chat.message.IMessage;
import ru.atom.chat.message.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HistoryReader {
    private File newFile;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HistoryWriter.class);



    public HistoryReader(String filename) throws IOException {
        newFile = new File(filename);
    }

    public Queue<IMessage> readHistory() {
        Queue<IMessage> messages = new ConcurrentLinkedQueue<>();
        try (BufferedReader br = new BufferedReader(new FileReader(newFile))) {
            while (true) {
                String name = br.readLine();
                //privet kostyl =)
                if (name == null)
                    break;
                String data = br.readLine();
                String date = br.readLine();

                Message newMes = new Message(name, data, date);

                messages.add(newMes);
            }
        } catch (FileNotFoundException fnfe) {
            log.error("File history.txt not found");
        } catch (IOException e) {
            log.error("IO Exception");
        }
        return messages;
    }
}
