package ru.atom.model;

import java.util.Date;

/**
 * Created by dmitriy on 26.03.17.
 */
public class User {
    private String name;
    private transient String password;
    private Date timestamp;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        timestamp = new Date(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        return getTimestamp() != null ? getTimestamp().equals(user.getTimestamp()) : user.getTimestamp() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getTimestamp() != null ? getTimestamp().hashCode() : 0);
        return result;
    }
}
