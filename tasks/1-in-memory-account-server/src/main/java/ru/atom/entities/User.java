package ru.atom.entities;

/**
 * Created by ikozin on 28.03.17.
 */
public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        } else {
            return (username.equals(((User) obj).getUsername()));
        }
    }

    @Override
    public int hashCode() {
        return (username + password).hashCode();
    }
}
