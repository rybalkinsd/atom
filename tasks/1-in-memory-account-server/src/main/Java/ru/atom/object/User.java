package ru.atom;

/**
 * Created by Fella on 26.03.2017.
 */
public class User {
    private String login;
    private String password;

    private int id;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (password != user.password) return false;
        return login != null ? login.equals(user.login) : user.login == null;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
