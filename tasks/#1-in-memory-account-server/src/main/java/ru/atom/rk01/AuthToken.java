package ru.atom.rk01;

/**
 * Created by dmbragin on 3/28/17.
 */
public class AuthToken implements Token {
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthToken authToken = (AuthToken) o;

        return value != null ? value.equals(authToken.value) : authToken.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public AuthToken(String username) {
        //todo generate token
        this.value = String.valueOf(username.hashCode());
    }

    public String getValue() {
        return value;
    }
}
