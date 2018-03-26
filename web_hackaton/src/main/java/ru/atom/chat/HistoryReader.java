package ru.atom.chat;

import ru.atom.chat.message.Message;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HistoryReader {
    private File newFile;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HistoryWriter.class);



    public HistoryReader(String filename) throws IOException
    {
        newFile = new File(filename);
    }

    public Queue<Message> readHistory()
    {
        Queue<Message> q = new ConcurrentLinkedQueue<>();
        try(BufferedReader br = new BufferedReader(new FileReader(newFile)))
        {
            while(1 > 0) {
                String name = br.readLine();
                //privet kostyl =)
                if(name == null)
                    break;
                String data = br.readLine();
                String date = br.readLine();

                Message newMes = new Message(name, data, date);

                q.add(newMes);
            }
        }
        catch (FileNotFoundException fnfe)
        {
            log.error("File history.txt not found");
        }
        catch (IOException e)
        {
            log.error("IO Exception");
        }
        return q;
    }

    public static void main(String[] args) {
        try {
            HistoryReader hr = new HistoryReader("history.txt");
            Queue<Message> q = hr.readHistory();
            for (Message m :q) {
                System.out.println(m.getUserName() + ": " + m.getMessageBody() + "\t" + m.getTime());
            }

        } catch (FileNotFoundException e) {
            log.error("FILE NOT FOUND");
        } catch (IOException ioe) {

            ioe.printStackTrace();
            log.error("IOE Exception");
        }

    }
}
