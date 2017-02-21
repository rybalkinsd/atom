package model;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Token {
    private Long token;

    public Token(Long token) {
        this.token = token;
    }

    public Token(String rawToken) {
        this.token = Long.parseLong(rawToken);
    }

    public Long getToken() {
        return this.token;
    }

    public static Token generateToken() {
        Random random = new Random();
        return new Token(random.nextLong());
    }

    @Override
    public boolean equals(@NotNull Object object) {
        if (object.getClass() != Token.class) return false;
        Token token = (Token) object;
        return (this.token.equals(token.getToken()));
    }

    @Override
    public int hashCode() {
        int k = 7;
        int sum = 0;
        for (int i = 0; i < this.token.toString().length(); i++ ) {
            sum =+ k*this.token.toString().charAt(i);
        }
        return sum;
    }

}
