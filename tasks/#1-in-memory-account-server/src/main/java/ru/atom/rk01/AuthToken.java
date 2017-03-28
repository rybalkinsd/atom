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

    public AuthToken() {
        this.value = null;
    }

    public AuthToken(String username) {
        //todo generate token
        this.value = String.valueOf(username.hashCode());
    }

    @Override
    public void setTokenString(String tokenString) {
        this.value = tokenString.split("Bearer ")[1];
    }

    @Override
    public String getTokenString() {
        return value;
    }
}
