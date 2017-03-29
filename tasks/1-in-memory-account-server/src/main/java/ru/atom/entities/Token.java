package ru.atom.entities;

/**
 * Created by ikozin on 28.03.17.
 */
public class Token {
    private Long value;

    public Token(User user) {
        long temp = user.hashCode();
        value = temp;
    }

    public Token(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if  (!(obj instanceof Token)) {
            return false;
        } else {
            Token token = (Token)obj;
            return token.hashCode() == this.hashCode();
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
