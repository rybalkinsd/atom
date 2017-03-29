package ru.atom.http.server;

import java.util.Random;

/**
 * Created by hpechka on 29.03.2017.
 */
public class Token {
    private Long token;

    public Token(){
        Random rand = new Random();
        token = new Long(rand.nextLong());
    }

    public Token(String s){
        token = Long.parseLong(s);
    }

    @Override
    public String toString() {
        return token.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token1 = (Token) o;

        return token != null ? token.equals(token1.token) : token1.token == null;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
