package ru.atom.base;

/**
 * Created by mkai on 3/26/17.
 */
public class Token {
    private String valueToken;

    public Token(long valueToken) {
        this.valueToken = Long.toString(valueToken);
    }

    public String getValueToken() {
        return valueToken;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Token token1 = (Token) obj;

        return valueToken == token1.valueToken;
    }

    @Override
    public int hashCode() {
        return valueToken.hashCode();
    }
}
