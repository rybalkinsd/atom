package ru.atom.chat.message;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Message implements IMessage {
    private String userName;
    private String body;
    private Date date;


    public Message(String userName, String body) {
        this.userName = userName;
        this.body = body;
        this.date = new Date();
    }

    public Message(String userName, String body, String time) {
        this.userName = userName;
        this.body = body;
        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        try {
            this.date = format.parse(time);
        } catch (ParseException PE) {
            this.date = new Date();
        }
    }

    @Override
    public String toString() {
        String returnStr = new String();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        String time = formatForDateNow.format(date);
        returnStr = "[" + this.userName + "]:" + this.body + "\t(" + time + ")";
        return returnStr;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getMessageBody() {
        return this.body;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public String getTime() {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss a zzz");
        String time = formatForDateNow.format(date);
        return time;
    }
}
