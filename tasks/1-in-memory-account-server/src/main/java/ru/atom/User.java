package ru.atom;

public class User {

    protected String nickname;
    protected String password;

    public User() {

    }

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public void setName(String nickname) {
        this.nickname = nickname;
    }

    public void setPass(String password) {
        this.password = password;
    }

    public String getName() {
        return nickname;
    }

    public String getPass() {
        return password;
    }
}
