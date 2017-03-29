package ru.atom.persons;

/**
 * Created by BBPax on 23.03.17.
 */
public class User {
    private String userName;
    private transient String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User temp = (User)obj;
            return userName.equals(temp.getUserName())
                    && password.equals(temp.getPassword());
        }
        return false;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
