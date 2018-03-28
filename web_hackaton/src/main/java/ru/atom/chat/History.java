package ru.atom.chat;

import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
class History {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    void writeHistory(Triplet<String, Date, String> messages) {
        String CurMas = messages.getValue0() + " " + messages.getValue1().toString() + " " + messages.getValue2()  + "\n";
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(History.class.getClassLoader()
                .getResource("history.txt").getPath(),true))) {
            bufferedWriter.write(CurMas);
        } catch(IOException e){
            log.error(e.getLocalizedMessage());
        } catch(NullPointerException e) {
            log.error("can`t load 'history.txt'");
        }
    }

    Queue<Triplet<String, Date, String>> loadHistory() {
        Queue<Triplet<String, Date, String>> messages = new ConcurrentLinkedQueue<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(History.class.getClassLoader()
                .getResource("history.txt").getPath()))) {
            String s;
            while((s = bufferedReader.readLine()) != null)
                s += "\n";
                //messages.add(new Triplet<>("s", new Date(), " try \n"));
        } catch(IOException e){
            log.error(e.getLocalizedMessage());
        } catch(NullPointerException e) {
            log.error("can`t load 'history.txt'");
            }
            return messages;

    }

}
