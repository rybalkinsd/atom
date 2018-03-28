package ru.atom.chat.user;

import java.util.Date;

public class User implements IUser {

    private String userName;
    private String password = "";
    private Date lastDate;
    private boolean isActive;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        if (!this.password.equals(password)) {
            throw new RuntimeException("wrong password");
        }
        this.isActive = true;
        this.lastDate = new Date();
    }

    public boolean login(String password) {
        boolean checking = passCheck(password);
        this.isActive = checking;
        return checking;
    }

    public boolean passCheck(String password) {
        if (!this.password.equals(password)) {
            return false;
        }
        return true;
    }

    public void logout() {
        this.isActive = false;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public Date getLastDate() {
        return this.lastDate;
    }

    public void setLastDate(Date msgDate) {
        this.lastDate = msgDate;
    }

    public boolean spamCheck() {
        // если прошло < 1 секунды, спам

        if (new Date().getTime() - lastDate.getTime() < 2500) {
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
