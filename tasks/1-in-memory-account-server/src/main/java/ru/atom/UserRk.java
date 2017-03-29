package ru.atom;

/**
 * Created by pavel on 23.03.17.
 */
public class UserRk {
    private String name;
    private String password;

    public UserRk(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRk userRk = (UserRk) o;

        if (name != null ? !name.equals(userRk.name) : userRk.name != null) return false;
        return password != null ? password.equals(userRk.password) : userRk.password == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
