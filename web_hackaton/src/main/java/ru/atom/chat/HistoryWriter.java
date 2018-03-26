package ru.atom.chat;

import ru.atom.chat.message.IMessage;
import ru.atom.chat.message.Message;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HistoryWriter {

    private File newFile;
    private BufferedWriter bw;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HistoryWriter.class);

    HistoryWriter(String filename)  throws IOException
    {
        newFile = new File(filename);
        newFile.createNewFile();



    }


    public void saveHistory(Queue<Message> q)
    {

        try(BufferedWriter newBW =  new BufferedWriter(new FileWriter(newFile)))
        {

            for (Message mes: q) {

                    newBW.write(mes.getUserName());
                    newBW.newLine();
                    newBW.write(mes.getMessageBody());
                    newBW.newLine();
                    newBW.write(mes.getTime());
                    newBW.newLine();

            }

        }
        catch (IOException ioe)
        {
            log.error("Fatal IOException error");
        }
    }

    public static void main(String[] args) {
        Message mes1 = new Message("Danilo", "DAROVA DA DAROVA");
        //Message mes2 = new Message("sadasdo", "Dsadasd");
        Message mes3 = new Message("asd", "asdsad");
        Queue<Message> q = new ConcurrentLinkedQueue<>();
        q.add(mes1);
       // q.add(mes2);
        q.add(mes3);
        try {
            HistoryWriter hw = new HistoryWriter("history.txt");
            hw.saveHistory(q);
        }
        catch (IOException e)
        {
            log.error("error creating file");
        }


    }
}
