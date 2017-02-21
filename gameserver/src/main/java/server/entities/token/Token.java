package server.entities.token;

import org.jetbrains.annotations.NotNull;

public class Token {

    @NotNull
    private Long token;

    public Token(@NotNull Long token) {
        this.token = token;
    }

    public Long getToken() {
        return this.token;
    }

    public void setToken(@NotNull Long token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object that) {
        if (that == null || that.getClass() != getClass()) return false;
        if (this == that) return true;

        Token castToken = (Token) that;
        return token.equals(castToken.getToken());
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    @Override
    public String toString() {
        return "Token(" +
                "token=" + token +
                ')';
    }

}
