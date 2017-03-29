package token;

import resources.User;

/**
 * Created by Robin on 28.03.2017.
 */
public class Token {
    private Long token;

    public Token(User user) {
        this.token = (long) user.hashCode();
    }

    public Token(String tokenStr) {
        this.token = Long.parseLong(tokenStr);
    }

    @Override
    public String toString() {
        return "Token{" +
                "token=" + token +
                '}';
    }

    public Long getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        return token != null ? token.equals(token1.token) : token1.token == null;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
