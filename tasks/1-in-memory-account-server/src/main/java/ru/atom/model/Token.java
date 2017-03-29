package ru.atom.model;

public class Token {
    private long id;

    public Token(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return (int)id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Token)) return false;
        Token token = (Token) o;
        return id == token.id;
    }
}