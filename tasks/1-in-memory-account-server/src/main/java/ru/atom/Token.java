package ru.atom;


/**
 * Created by Vlad on 26.03.2017.
 */
public class Token {

    private long token;
    private User user;

    public Token(User user) {
        this.user = user;
        this.token = user.getName().hashCode() + user.getPassword().hashCode() * 100_000L;
    }

    public long getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token)
            return this.user.equals(((Token) obj).getUser()) && this.token == ((Token) obj).token;
        else
            return false;
    }

    @Override
    public String toString() {
        return String.valueOf(token);
    }
}
