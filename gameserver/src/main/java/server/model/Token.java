package server.model;

import org.jetbrains.annotations.NotNull;

public class Token {

    @NotNull
    private Long token;

    public Token(@NotNull Long token) {
        this.token = token;
    }

    @NotNull
    public Long getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return token.equals(token1.token);
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }
}
