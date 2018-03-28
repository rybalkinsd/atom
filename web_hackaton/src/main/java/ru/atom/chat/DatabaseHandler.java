package ru.atom.chat;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
class DatabaseHandler {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    void put(String what) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(DatabaseHandler.class.getClassLoader()
                .getResource("History.txt").getPath(),true))) {
            bw.write(what + "\n");
        } catch(IOException ex){
            log.warn("Unable to write to history");
        } catch(NullPointerException e) {
            log.warn("Unable to get resource 'History.txt'");
        }
    }

    Queue<String> toQueue() {
        Queue<String> messages = new ConcurrentLinkedQueue<>();
        try(BufferedReader br = new BufferedReader(new FileReader(DatabaseHandler.class.getClassLoader()
                .getResource("History.txt").getPath()))) {
                    String s;
                    while((s = br.readLine()) != null)
                        messages.add(s);

        } catch(IOException ex){
            log.warn("Unable to read");
        } catch(NullPointerException e) {
            log.warn("Unable to get resource 'History.txt'");
        }
        return messages;

    }

}
