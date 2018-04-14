package ru.atom.lecture08.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;
import ru.atom.lecture08.websocket.queues.MessagesQueue;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String topic = "";
    private String sender = "";
    private String msg = "";

    @JsonCreator
    public Message(@JsonProperty(value = "topic", required = true) String topic,
                   @JsonProperty(value = "sender", required = true) String sender,
                   @JsonProperty(value = "msg", required = true) String msg) {
        this.msg = msg.substring(4);
        this.sender = sender.substring(5);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public String getMsg() {
        return msg;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        sender = HtmlUtils.htmlEscape(sender);
        try {
            msg = java.net.URLDecoder.decode(msg, "UTF-8");
        } catch(Exception e){
        }

        msg = HtmlUtils.htmlEscape(msg);

        if (msg.contains("http")) {
            int linkEnd = msg.indexOf("http");
            while ((linkEnd < msg.length()) && (msg.charAt(linkEnd) != ' ')) {
                linkEnd++;
            }
            String link = msg.substring(msg.indexOf("http"), linkEnd);
            msg = msg.replace(link,  "<a href=\"" + link +"\" target=\"_blank\">" + link + "</a>" );
        }

        Date date = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm:ss");
        String msgg = "<span style=\"color:#999999\">{" + tFormat.format(date) + "}</span>" + " [" + sender + "]: "+msg;
        MessagesQueue.getMessages().add(msgg);
        try{
            FileWriter hFile = new FileWriter("hist.txt", true);
            hFile.append(msgg);
            hFile.append("\n");
            hFile.close();
        }
        catch (IOException e) {
        }
        return msgg;
    }
}
