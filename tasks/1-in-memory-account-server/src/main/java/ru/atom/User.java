package ru.atom;

/**
 * Created by Vlad on 26.03.2017.
 */
public class User {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean fullEquals(Object obj) {
        if (obj instanceof User)
            return this.name.equals(((User) obj).getName()) && this.password.equals(((User) obj).getPassword());
        else
            return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User)
            return this.name.equals(((User) obj).getName());
        else
            return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
