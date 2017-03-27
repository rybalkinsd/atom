package io.github.rentgen94;

/**
 * Created by Western-Co on 26.03.2017.
 */
public class Token {
    private Long token;

    public Token(User user) {
        this.token = 4L * user.getName().hashCode() + user.getPassword();
    }

    public Token(String strToken) {
        if (strToken.length() > 0) {
            this.token = Long.parseLong(strToken);
        } else {
            this.token = -1L;
        }
    }

    public long getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        Token other = (Token) o;
        if (token != (other.getToken())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(getToken());
    }
}
