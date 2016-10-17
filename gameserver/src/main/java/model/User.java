package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class User {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(@NotNull Object object) {
        if (object.getClass() != User.class) return false;
        User user = (User) object;
        return (this.userName.equals(user.userName));
    }

    @Override
    public int hashCode() {
        int k = 7;
        int sum = 0;
        for (int i = 0; i < this.userName.length(); i++ ) {
            sum =+ k*this.userName.charAt(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + this.userName + '\'' +
                '}';
    }
}
