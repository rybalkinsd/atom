package ru.atom;

/**
 * Created by user on 28.03.2017.
 */
public class Token {
    private long token;
    private User user;

    public Token(User user) {
        this.user = user;
        this.token = user.getLogin().hashCode() + user.getPassword().hashCode();
    }

    public long getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
