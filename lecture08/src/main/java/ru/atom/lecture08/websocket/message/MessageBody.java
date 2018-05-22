package ru.atom.lecture08.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;
import ru.atom.lecture08.websocket.queues.MessagesQueue;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageBody {
    private String sender = "";
    private String msg = "";

    @JsonCreator
    public MessageBody(@JsonProperty(value = "sender", required = true) String sender,
                          @JsonProperty(value = "msg", required = true) String msg) {
        this.msg = msg;
        this.sender = sender;
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
        msg = HtmlUtils.htmlEscape(msg);

        /*if (!usersOnline.containsKey(sender)) {
            return ResponseEntity.badRequest().body("You are not logged in:(");
        }

        if (msgCount.get(sender) > 10) {
            return ResponseEntity.badRequest().body("You are banned:(\n 10 seconds cooldown");
        }*/

        if (msg.contains("http")) {
            int linkEnd = msg.indexOf("http");
            while ((linkEnd < msg.length()) && (msg.charAt(linkEnd) != ' ')) {
                linkEnd++;
            }
            String link = msg.substring(msg.indexOf("http"), linkEnd);
            msg = msg.replace(link,  "<a href=\"" + link +"\" target=\"_blank\">" + link + "</a>" );
        }

        //msgCount.put(sender, msgCount.get(sender) + 1);
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
