package ru.atom.chat.User;
import ru.atom.chat.message.Message;

import java.util.Date;

public class User implements IUser {

    private String userName;
    private String password;
    private Date lastDate;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        lastDate = new Date();
    }

    public Date getLastDate() {
        return this.lastDate;
    }

    public void setLastDate(Date msgDate) {
        this.lastDate = msgDate;
    }

    public boolean spamCheck(Message newMsg) {
        // если прошло < 1 секунды, спам
        if (newMsg.getDate().getTime() - lastDate.getTime() < 3000) {
            return false;
        }
        return true;
    }


    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
