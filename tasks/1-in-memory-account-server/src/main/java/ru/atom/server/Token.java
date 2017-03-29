package ru.atom.server;

/**
 * Created by Ксения on 25.03.2017.
 */

public class Token {
    private Integer id;
    private Long token;

    public Token(Integer id, Long token) {
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }
}
